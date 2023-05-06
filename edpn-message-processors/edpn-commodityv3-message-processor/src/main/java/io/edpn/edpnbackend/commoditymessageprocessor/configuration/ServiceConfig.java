package io.edpn.edpnbackend.commoditymessageprocessor.configuration;

import io.edpn.edpnbackend.commoditymessageprocessor.application.service.SynchronizedReceiveCommodityMessageService;
import io.edpn.edpnbackend.commoditymessageprocessor.application.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.edpnbackend.commoditymessageprocessor.domain.repository.*;
import io.edpn.edpnbackend.domain.repository.*;
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
            HistoricStationCommodityMarketDatumRepository historicStationCommodityMarketDatumRepository,
            SchemaLatestTimestampRepository schemaLatestTimestampRepository,
            StationSystemRepository stationSystemRepository) {
        return new SynchronizedReceiveCommodityMessageService(systemRepository, stationRepository, commodityRepository, economyRepository, historicStationCommodityMarketDatumRepository, schemaLatestTimestampRepository, stationSystemRepository);
    }
}
