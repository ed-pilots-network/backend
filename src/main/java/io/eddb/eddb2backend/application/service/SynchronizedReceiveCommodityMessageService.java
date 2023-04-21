package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;
import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.HistoricStationCommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.SchemaLatestTimestampEntity;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUseCase;
import io.eddb.eddb2backend.domain.repository.CommodityRepository;
import io.eddb.eddb2backend.domain.repository.EconomyRepository;
import io.eddb.eddb2backend.domain.repository.HistoricStationCommodityRepository;
import io.eddb.eddb2backend.domain.repository.SchemaLatestTimestampRepository;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.domain.repository.SystemRepository;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static io.eddb.eddb2backend.domain.util.CollectionUtil.toList;

@RequiredArgsConstructor
public class SynchronizedReceiveCommodityMessageService implements ReceiveCommodityMessageUseCase {

    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;
    private final CommodityRepository commodityRepository;
    private final EconomyRepository economyRepository;
    private final HistoricStationCommodityRepository historicStationCommodityRepository;
    private final SchemaLatestTimestampRepository schemaLatestTimestampRepository;

    @Override
    @Transactional
    public synchronized void receive(CommodityMessage.V3 commodityMessage) {
        System.out.println("ReceiveCommodityMessageService.receive -> commodityMessage: " + commodityMessage);

        var updateTimestamp = commodityMessage.getMessageTimeStamp();
        String schemaRef = commodityMessage.getSchemaRef();
        if (!schemaLatestTimestampRepository.isAfterLatest(schemaRef, updateTimestamp)) {
            System.out.println("ReceiveCommodityMessageService.receive -> the message is not newer than what we already processed, skipping");
            return; // the message is not newer than what we already processed, skipping
        } else {
            schemaLatestTimestampRepository.createOrUpdate(SchemaLatestTimestampEntity.builder()
                    .schema(schemaRef)
                    .timestamp(updateTimestamp)
                    .build());
        }

        CommodityMessage.V3.Message payload = commodityMessage.getMessage();
        CommodityMessage.V3.Commodity[] commodities = payload.getCommodities();
        CommodityMessage.V3.Economy[] economies = payload.getEconomies();
        long marketId = payload.getMarketId();
        String systemName = payload.getSystemName();
        String stationName = payload.getStationName();
        String[] prohibitedCommodities = payload.getProhibited();


        // find system, if not found create
        var system = systemRepository.findOrCreateByName(systemName);
        // update system info

        //save system
        systemRepository.update(system);
        System.out.println("ReceiveCommodityMessageService.receive -> system: " + system);

        // find station, if not found create
        var station = stationRepository.findOrCreateByMarketId(marketId);

        //parse message info
        Collection<UUID> prohibitedCommodityIds = Optional.ofNullable(prohibitedCommodities)
                .map(arr -> Arrays.stream(arr)
                        .map(commodityRepository::findOrCreateByName)
                        .map(CommodityEntity::getId)
                        .toList())
                .orElse(Collections.emptyList());

        Map<UUID, Double> economyEntityIdProportionMap = Optional.ofNullable(economies)
                .map(arr -> Arrays.stream(arr)
                        .map(economy -> {
                            UUID id = economyRepository.findOrCreateByName(economy.getName()).getId();
                            double proportion = economy.getProportion();
                            return new AbstractMap.SimpleEntry<>(id, proportion);
                        })
                        .filter(entry -> Objects.nonNull(entry.getKey()))
                        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue, (o1, o2) -> o1)))
                .orElse(Collections.emptyMap());

        if (Objects.nonNull(commodities)) {
            Arrays.stream(commodities)
                    .forEach(commodity -> {
                        UUID commodityId = commodityRepository.findOrCreateByName(commodity.getName()).getId();

                        var hsce = HistoricStationCommodityEntity.builder()
                                .stationId(station.getId())
                                .commodityId(commodityId)
                                .timestamp(updateTimestamp)
                                .meanPrice(commodity.getMeanPrice())
                                .buyPrice(commodity.getBuyPrice())
                                .sellPrice(commodity.getSellPrice())
                                .stock(commodity.getStock())
                                .stockBracket(commodity.getStockBracket())
                                .demand(commodity.getDemand())
                                .demandBracket(commodity.getDemandBracket())
                                .statusFlags(toList(commodity.getStatusFlags()))
                                .build();

                        historicStationCommodityRepository.create(hsce);
                    });
        }

        // update station info
        station.setName(stationName);
        station.setMarketUpdatedAt(updateTimestamp);
        station.setHasCommodities(true);
        station.setProhibitedCommodityIds(prohibitedCommodityIds);
        station.setEconomyEntityIdProportionMap(economyEntityIdProportionMap);

        // save station
        stationRepository.update(station);

        System.out.println("ReceiveCommodityMessageService.receive -> station: " + station);
    }
}
