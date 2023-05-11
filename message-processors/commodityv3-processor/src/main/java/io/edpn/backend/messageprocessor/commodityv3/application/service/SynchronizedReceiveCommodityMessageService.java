package io.edpn.backend.messageprocessor.commodityv3.application.service;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.CommodityEntity;
import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEntity;
import io.edpn.backend.messageprocessor.commodityv3.application.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.CommodityRepository;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.EconomyRepository;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.HistoricStationCommodityMarketDatumRepository;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationRepository;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.SystemRepository;
import java.time.LocalDateTime;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static io.edpn.backend.messageprocessor.domain.util.CollectionUtil.toList;

@RequiredArgsConstructor
@Slf4j
public class SynchronizedReceiveCommodityMessageService implements ReceiveCommodityMessageUseCase {
    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;
    private final CommodityRepository commodityRepository;
    private final EconomyRepository economyRepository;
    private final HistoricStationCommodityMarketDatumRepository historicStationCommodityMarketDatumRepository;

    @Override
    @Transactional
    public synchronized void receive(CommodityMessage.V3 commodityMessage) {
        long start = System.nanoTime();
        if (log.isDebugEnabled()) {
            log.debug("ReceiveCommodityMessageService.receive -> commodityMessage: " + commodityMessage);
        }

        var updateTimestamp = commodityMessage.getMessageTimeStamp();

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
        var station = stationRepository.findByMarketId(marketId).orElseGet(() -> {
            var tempStation = stationRepository.findOrCreateBySystemIdAndStationName(system.getId(), stationName);
            tempStation.setEdMarketId(marketId);
            return tempStation;
        });

        // update station
        Collection<UUID> prohibitedCommodityIds = getProhibitedCommodityIds(prohibitedCommodities);
        Map<UUID, Double> economyEntityIdProportionMap = getEconomyEntityIdProportionMap(economies);
        station.setMarketUpdatedAt(updateTimestamp);
        station.setHasCommodities(true);
        station.setProhibitedCommodityIds(prohibitedCommodityIds);
        station.setEconomyEntityIdProportionMap(economyEntityIdProportionMap);
        stationRepository.update(station);

        //save market data
        saveCommodityMarketData(updateTimestamp, commodities, station);

        if (log.isDebugEnabled()) {
            log.debug("ReceiveCommodityMessageService.receive -> station: " + station);
        }
        if (log.isTraceEnabled()) {
            log.trace("ReceiveCommodityMessageService.receive -> took " + (System.nanoTime() - start) + " nanosecond");
        }

        log.info("ReceiveCommodityMessageService.receive -> the message has been processed");
    }

    private void saveCommodityMarketData(LocalDateTime updateTimestamp, CommodityMessage.V3.Commodity[] commodities, StationEntity station) {
        if (Objects.nonNull(commodities)) {
            Arrays.stream(commodities)
                    .forEach(commodity -> {
                        UUID commodityId = commodityRepository.findOrCreateByName(commodity.getName()).getId();
                        UUID stationId = station.getId();

                        if (historicStationCommodityMarketDatumRepository.getByStationIdAndCommodityIdAndTimestamp(stationId, commodityId, updateTimestamp).isEmpty()) {
                            var hsce = HistoricStationCommodityMarketDatumEntity.builder()
                                    .stationId(stationId)
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

                            historicStationCommodityMarketDatumRepository.create(hsce);
                        }

                        //data cleanup
                        historicStationCommodityMarketDatumRepository.cleanupRedundantData(stationId, commodityId);
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
}
