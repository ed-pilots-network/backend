package io.eddb.eddb2backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.application.service.GetStationService;
import io.eddb.eddb2backend.application.service.ReceiveCommodityMessageService;
import io.eddb.eddb2backend.application.usecase.GetStationUsecase;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUsecase;
import io.eddb.eddb2backend.infrastructure.eddn.EddnMessageHandler;
import io.eddb.eddb2backend.infrastructure.eddn.processor.CommodityV3MessageProcessor;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public GetStationService getStationService() {
        return new GetStationService();
    }

    @Bean
    public GetStationUsecase getStationUsecase(GetStationService getStationService) {
        return getStationService;
    }

    @Bean
    public EddnMessageHandler eddnMessageHandler(@Qualifier("eddnTaskExecutor") TaskExecutor taskExecutor, @Qualifier("eddnRetryTemplate") RetryTemplate retryTemplate, ObjectMapper objectMapper, CommodityV3MessageProcessor commodityV3MessageProcessor) {
        return new EddnMessageHandler(taskExecutor, retryTemplate, objectMapper, commodityV3MessageProcessor);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ReceiveCommodityMessageUsecase receiveCommodityMessageService(
            SystemEntityMapper systemEntityMapper,
            StationEntityMapper stationEntityMapper,
            CommodityEntityMapper commodityEntityMapper,
            EconomyEntityMapper economyEntityMapper,
            StationCommodityEntityMapper stationCommodityEntityMapper,
            HistoricStationCommodityEntityMapper historicStationCommodityEntityMapper) {
        return new ReceiveCommodityMessageService(systemEntityMapper, stationEntityMapper, commodityEntityMapper, economyEntityMapper, stationCommodityEntityMapper, historicStationCommodityEntityMapper);
    }

    @Bean
    public CommodityV3MessageProcessor commodityV3MessageProcessor(ReceiveCommodityMessageUsecase receiveCommodityMessageUsecase) {
        return new CommodityV3MessageProcessor(receiveCommodityMessageUsecase);
    }
}
