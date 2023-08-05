package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.kafka.dto.mapper.KafkaMessageMapper;
import io.edpn.backend.trade.application.dto.mapper.MessageMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeKafkaMapperConfig")
public class KafkaMapperConfig {

    @Bean(name = "tradeMessageMapper")
    public MessageMapper messageMapper() {
        return new KafkaMessageMapper();
    }
}
