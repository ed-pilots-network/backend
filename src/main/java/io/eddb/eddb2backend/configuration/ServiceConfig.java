package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.application.service.SynchronizedReceiveCommodityMessageService;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUseCase;
import io.eddb.eddb2backend.domain.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUsecase(
            SystemRepository systemRepository,
            StationRepository stationRepository,
            CommodityRepository commodityRepository,
            EconomyRepository economyRepository,
            HistoricStationCommodityRepository historicStationCommodityRepository,
            SchemaLatestTimestampRepository schemaLatestTimestampRepository) {
        return new SynchronizedReceiveCommodityMessageService(systemRepository, stationRepository, commodityRepository, economyRepository, historicStationCommodityRepository, schemaLatestTimestampRepository);
    }
}