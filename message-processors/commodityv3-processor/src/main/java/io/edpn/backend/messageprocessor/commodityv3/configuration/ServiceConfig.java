package io.edpn.backend.messageprocessor.commodityv3.configuration;

import io.edpn.backend.messageprocessor.commodityv3.application.service.SynchronizedReceiveCommodityMessageService;
import io.edpn.backend.messageprocessor.commodityv3.application.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.*;
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
            HistoricStationCommodityMarketDatumRepository historicStationCommodityMarketDatumRepository) {
        return new SynchronizedReceiveCommodityMessageService(systemRepository, stationRepository, commodityRepository, economyRepository, historicStationCommodityMarketDatumRepository);
    }
}
