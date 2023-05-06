package io.edpn.edpnbackend.commoditymessageprocessor.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.edpnbackend.commoditymessageprocessor.application.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.edpnbackend.commoditymessageprocessor.infrastructure.kafka.processor.CommodityV3MessageProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EddnMessagesProcessorBeanConfig {
    @Bean
    public CommodityV3MessageProcessor commodityV3MessageProcessor(ReceiveCommodityMessageUseCase receiveCommodityMessageUsecase, ObjectMapper objectMapper) {
        return new CommodityV3MessageProcessor(receiveCommodityMessageUsecase, objectMapper);
    }
}
