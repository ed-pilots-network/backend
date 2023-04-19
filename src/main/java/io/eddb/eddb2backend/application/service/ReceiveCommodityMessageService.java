package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;
import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.EconomyEntity;
import io.eddb.eddb2backend.application.dto.persistence.HistoricStationCommodityEntity;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUsecase;
import io.eddb.eddb2backend.domain.repository.*;
import io.eddb.eddb2backend.domain.util.TimestampConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static io.eddb.eddb2backend.domain.util.CollectionUtil.toList;

@RequiredArgsConstructor
public class ReceiveCommodityMessageService implements ReceiveCommodityMessageUsecase {


    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;
    private final CommodityRepository commodityRepository;
    private final EconomyRepository economyRepository;
    private final HistoricStationCommodityRepository historicStationCommodityRepository;

    @Override
    @Transactional
    public void receive(CommodityMessage.V3 commodityMessage) {
        System.out.println("ReceiveCommodityMessageService.receive -> commodityMessage: " + commodityMessage);

        CommodityMessage.V3.Message payload = commodityMessage.getMessage();
        CommodityMessage.V3.Commodity[] commodities = payload.getCommodities();
        CommodityMessage.V3.Economy[] economies = payload.getEconomies();
        long marketId = payload.getMarketId();
        String systemName = payload.getSystemName();
        String stationName = payload.getStationName();
        String[] prohibitedCommodities = payload.getProhibited();
        String timestamp = payload.getTimestamp();

        //convert timestamp to localDateTime
        var updateTimestamp = TimestampConverter.convertToLocalDateTime(timestamp);

        // find system, if not found create
        var system = systemRepository.findOrCreateByName(systemName);
        // update system info

        //save system
        systemRepository.update(system);
        System.out.println("ReceiveCommodityMessageService.receive -> system: " + system);

        // find station, if not found create
        var station = stationRepository.findOrCreateByMarketId(marketId);

        //parse message info
        Collection<CommodityEntity.Id> prohibitedCommodityIds = Optional.ofNullable(prohibitedCommodities)
                .map(arr -> Arrays.stream(arr)
                        .map(commodityRepository::findOrCreateByName)
                        .map(CommodityEntity::getId)
                        .toList())
                .orElse(Collections.emptyList());

        Map<EconomyEntity.Id, Double> economyEntityIdProportionMap = Optional.ofNullable(economies)
                .map(arr -> Arrays.stream(arr)
                        .map(economy -> {
                            EconomyEntity.Id id = economyRepository.findOrCreateByName(economy.getName()).getId();
                            double proportion = economy.getProportion();
                            return new AbstractMap.SimpleEntry<>(id, proportion);
                        })
                        .filter(entry -> Objects.nonNull(entry.getKey()))
                        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue, (o1, o2) -> o1)))
                .orElse(Collections.emptyMap());

        if (Objects.nonNull(commodities)) {
            Arrays.stream(commodities)
                    .forEach(commodity -> {
                        CommodityEntity.Id commodityId = commodityRepository.findOrCreateByName(commodity.getName()).getId();

                        HistoricStationCommodityEntity.Id hid = new HistoricStationCommodityEntity.Id(station.getId(), commodityId, updateTimestamp);
                        var hsce = HistoricStationCommodityEntity.builder()
                                .id(hid)
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
