package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;
import io.eddb.eddb2backend.application.dto.persistence.*;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUseCase;
import io.eddb.eddb2backend.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.eddb.eddb2backend.domain.util.CollectionUtil.toList;

@RequiredArgsConstructor
public class SynchronizedReceiveCommodityMessageService implements ReceiveCommodityMessageUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronizedReceiveCommodityMessageService.class);

    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;
    private final CommodityRepository commodityRepository;
    private final EconomyRepository economyRepository;
    private final HistoricStationCommodityMarketDatumRepository historicStationCommodityMarketDatumRepository;
    private final SchemaLatestTimestampRepository schemaLatestTimestampRepository;
    private final CommodityMarketDatumRepository commodityMarketDatumRepository;
    private final StationSystemRepository stationSystemRepository;

    @Override
    @Transactional
    public synchronized void receive(CommodityMessage.V3 commodityMessage) {
        LOGGER.debug("ReceiveCommodityMessageService.receive -> commodityMessage: " + commodityMessage);

        var updateTimestamp = commodityMessage.getMessageTimeStamp();
        String schemaRef = commodityMessage.getSchemaRef();

        //check if we should skip
        boolean isLatest;
        isLatest = isLatestMessageAndUpdateTimestamp(updateTimestamp, schemaRef);

        if (!isLatest) {
            LOGGER.info("ReceiveCommodityMessageService.receive -> the message is not newer than what we already processed, skipping");
            return;
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

        // find station, if not found create
        var station = stationRepository.findOrCreateByMarketId(marketId);

        //update the info for stationSystem linking
        updateSaveLinkBetweenStationAndSystem(system, station);

        // update station
        Collection<UUID> prohibitedCommodityIds = getProhibitedCommodityIds(prohibitedCommodities);
        Map<UUID, Double> economyEntityIdProportionMap = getEconomyEntityIdProportionMap(economies);
        station.setName(stationName);
        station.setMarketUpdatedAt(updateTimestamp);
        station.setHasCommodities(true);
        station.setProhibitedCommodityIds(prohibitedCommodityIds);
        station.setEconomyEntityIdProportionMap(economyEntityIdProportionMap);
        stationRepository.update(station);

        //save market data
        saveCommodityMarketData(updateTimestamp, commodities, station);

        LOGGER.debug("ReceiveCommodityMessageService.receive -> station: " + station);
    }

    private boolean isLatestMessageAndUpdateTimestamp(LocalDateTime updateTimestamp, String schemaRef) {
        boolean isLatest;
        if (!schemaLatestTimestampRepository.isAfterLatest(schemaRef, updateTimestamp)) {
            isLatest = false; // the message is not newer than what we already processed
        } else {
            // the message is newer than what we already processed, update ore create the timestamp
            schemaLatestTimestampRepository.createOrUpdate(SchemaLatestTimestampEntity.builder()
                    .schema(schemaRef)
                    .timestamp(updateTimestamp)
                    .build());
            isLatest = true;
        }
        return isLatest;
    }

    private void saveCommodityMarketData(LocalDateTime updateTimestamp, CommodityMessage.V3.Commodity[] commodities, StationEntity station) {
        if (Objects.nonNull(commodities)) {
            Arrays.stream(commodities)
                    .forEach(commodity -> {
                        UUID commodityId = commodityRepository.findOrCreateByName(commodity.getName()).getId();

                        var cmde = CommodityMarketDatumEntity.builder()
                                .id(UUID.randomUUID())
                                .meanPrice(commodity.getMeanPrice())
                                .buyPrice(commodity.getBuyPrice())
                                .sellPrice(commodity.getSellPrice())
                                .stock(commodity.getStock())
                                .stockBracket(commodity.getStockBracket())
                                .demand(commodity.getDemand())
                                .demandBracket(commodity.getDemandBracket())
                                .statusFlags(toList(commodity.getStatusFlags()))
                                .build();
                        commodityMarketDatumRepository.create(cmde);

                        var hsce = HistoricStationCommodityMarketDatumEntity.builder()
                                .id(UUID.randomUUID())
                                .stationId(station.getId())
                                .commodityId(commodityId)
                                .timestamp(updateTimestamp)
                                .marketDatumId(cmde.getId())
                                .build();

                        historicStationCommodityMarketDatumRepository.create(hsce);
                    });
        }
    }

    private Map<UUID, Double> getEconomyEntityIdProportionMap(CommodityMessage.V3.Economy[] economies) {
        return Optional.ofNullable(economies)
                .map(arr -> Arrays.stream(arr)
                        .map(economy -> {
                            UUID id = economyRepository.findOrCreateByName(economy.getName()).getId();
                            double proportion = economy.getProportion();
                            return new AbstractMap.SimpleEntry<>(id, proportion);
                        })
                        .filter(entry -> Objects.nonNull(entry.getKey()))
                        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue, (o1, o2) -> o1)))
                .orElse(Collections.emptyMap());
    }

    private Collection<UUID> getProhibitedCommodityIds(String[] prohibitedCommodities) {
        return Optional.ofNullable(prohibitedCommodities)
                .map(arr -> Arrays.stream(arr)
                        .map(commodityRepository::findOrCreateByName)
                        .map(CommodityEntity::getId)
                        .toList())
                .orElse(Collections.emptyList());
    }

    private void updateSaveLinkBetweenStationAndSystem(SystemEntity system, StationEntity station) {
        stationSystemRepository.findById(station.getId())
                .ifPresentOrElse(stationSystemEntity -> {
                    stationSystemEntity.setSystemId(system.getId());
                    stationSystemRepository.update(stationSystemEntity);
                }, () -> {
                    stationSystemRepository.create(new StationSystemEntity(station.getId(), system.getId()));
                });
    }
}
