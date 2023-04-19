package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;
import io.eddb.eddb2backend.application.dto.persistence.*;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUsecase;
import io.eddb.eddb2backend.domain.util.TimestampConverter;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.*;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReceiveCommodityMessageService implements ReceiveCommodityMessageUsecase {

    private final SystemEntityMapper systemEntityMapper;
    private final StationEntityMapper stationEntityMapper;
    private final CommodityEntityMapper commodityEntityMapper;
    private final EconomyEntityMapper economyEntityMapper;
    private final StationCommodityEntityMapper stationCommodityEntityMapper;
    private final HistoricStationCommodityEntityMapper historicStationCommodityEntityMapper;

    @Override
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
        var system = systemEntityMapper.findByName(systemName)
                .orElseGet(() -> {
                    SystemEntity s = SystemEntity.builder().name(systemName).build();
                    systemEntityMapper.insert(s);

                    return s;
                });
        // update system info

        //save system
        systemEntityMapper.update(system);
        System.out.println("ReceiveCommodityMessageService.receive -> system: " + system);

        // find station, if not found create
        var station = stationEntityMapper.findByMarketId(marketId)
                .orElseGet(() -> {
                    StationEntity s = StationEntity.builder()
                            .edMarketId(marketId)
                            .name(systemName)
                            .build();
                    stationEntityMapper.insert(s);

                    return s;
                });

        //parse message info
        Collection<CommodityEntity.Id> prohibitedCommodityIds = Arrays.stream(prohibitedCommodities)
                .map(commodityEntityMapper::findByName) //TODO make findOrCreate.
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(CommodityEntity::getId)
                .toList();

        Map<EconomyEntity.Id, Double> economyEntityIdProportionMap = Arrays.stream(economies)
                .map(economy -> {
                    EconomyEntity.Id id = economyEntityMapper.findByName(economy.getName())//TODO make findOrCreate
                            .map(EconomyEntity::getId)
                            .orElse(null);
                    double proportion = economy.getProportion();
                    return new AbstractMap.SimpleEntry<>(id, proportion);
                })
                .filter(entry -> Objects.nonNull(entry.getKey()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue, (o1, o2) -> o1));

        List<StationCommodityEntity.Id> stationCommodities = Arrays.stream(commodities)
                .map(c -> {
                    CommodityEntity.Id commodityId = commodityEntityMapper.findByName(c.getName())  //TODO make findOrCreate
                            .map(CommodityEntity::getId)
                            .orElse(null);

                    HistoricStationCommodityEntity.Id hid = new HistoricStationCommodityEntity.Id(station.getId(), commodityId, updateTimestamp);
                    var hsce = HistoricStationCommodityEntity.builder()
                            .id(hid)
                            .meanPrice(c.getMeanPrice())
                            .buyPrice(c.getBuyPrice())
                            .sellPrice(c.getSellPrice())
                            .stock(c.getStock())
                            .stockBracket(c.getStockBracket())
                            .demand(c.getDemand())
                            .demandBracket(c.getDemandBracket())
                            .statusFlags(c.getStatusFlags())
                            .build();

                    historicStationCommodityEntityMapper.insert(hsce);

                    StationCommodityEntity.Id id = new StationCommodityEntity.Id(station.getId(), commodityId);
                    var sce = StationCommodityEntity.builder()
                            .id(id)
                            .meanPrice(c.getMeanPrice())
                            .buyPrice(c.getBuyPrice())
                            .sellPrice(c.getSellPrice())
                            .stock(c.getStock())
                            .stockBracket(c.getStockBracket())
                            .demand(c.getDemand())
                            .demandBracket(c.getDemandBracket())
                            .statusFlags(c.getStatusFlags())
                            .build();

                    //TODO remove old sce
                    stationCommodityEntityMapper.insert(sce);

                    return sce.getId();
                })
                .filter(Objects::nonNull)
                .toList();


        // update station info
        station.setMarketUpdatedAt(updateTimestamp);
        station.setHasCommodities(true);
        station.setProhibitedCommodityIds(prohibitedCommodityIds);
        station.setEconomyEntityIdProportionMap(economyEntityIdProportionMap);
        station.setCommodities(stationCommodities);

        // save station
        stationEntityMapper.update(station);

        System.out.println("ReceiveCommodityMessageService.receive -> station: " + station);
    }
}
