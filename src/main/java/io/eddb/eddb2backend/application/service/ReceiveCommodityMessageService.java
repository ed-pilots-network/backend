package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;
import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUsecase;
import io.eddb.eddb2backend.domain.repository.CommodityRepository;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.domain.repository.SystemRepository;
import io.eddb.eddb2backend.domain.util.TimestampConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class ReceiveCommodityMessageService implements ReceiveCommodityMessageUsecase {

    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;
    private final CommodityRepository commodityRepository;

    @Override
    public void receive(CommodityMessage.V3 commodityMessage) {
        CommodityMessage.V3.Message payload = commodityMessage.getMessage();
        payload.getCommodities();
        payload.getEconomies();
        payload.getMarketId();
        String systemName = payload.getSystemName();
        String stationName = payload.getStationName();
        String[] prohibitedCommodities = payload.getProhibited();
        String timestamp = payload.getTimestamp();

        //convert timestamp to localDateTime
        var updateTimestamp = TimestampConverter.convertToLocalDateTime(timestamp);

        // find system, if not found create
        SystemEntity systemEntity = systemRepository.findByName(systemName).orElseGet(() -> {
            var newSystem = new SystemEntity();
            newSystem.setName(systemName);

            systemRepository.save(newSystem);
            // DEBUG
            System.out.println("newly saved system: " + newSystem.toString());
            return newSystem;
        });

        // find station, if not found create
        StationEntity stationEntity = stationRepository.findByName(stationName).orElseGet(() -> {
            var newStation = new StationEntity();
            newStation.setName(stationName);

            stationRepository.save(newStation);
            // DEBUG
            System.out.println("newly saved station: " + newStation.toString());
            return newStation;
        });

        //update system info
        systemEntity.getStationIds().add(stationEntity.getId());
        systemEntity.setLastUpdateTimeStamp(updateTimestamp);
        // TODO add other system relevant info
        //perisist received system info
        systemRepository.save(systemEntity);

        //update station info



        // --> set marketId
        // --> find economies and save for station
        // --> find commodities and set them as prohibited

        // find commodities and save economic info
    }
}
