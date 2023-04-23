package io.edpn.edpnbackend.configuration;

import io.edpn.edpnbackend.application.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.edpnbackend.infrastructure.kafka.processor.CommodityV3MessageProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EddnMessagesProcessorBeanConfig {
    @Bean
    public CommodityV3MessageProcessor commodityV3MessageProcessor(ReceiveCommodityMessageUseCase receiveCommodityMessageUsecase) {
        return new CommodityV3MessageProcessor(receiveCommodityMessageUsecase);
    }
}
