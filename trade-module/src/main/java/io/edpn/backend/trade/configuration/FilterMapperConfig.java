package io.edpn.backend.trade.configuration;

import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.LocateCommodityFilterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleEntityMapperConfig")
public class FilterMapperConfig {

    @Bean
    public LocateCommodityFilterMapper locateCommodityFilterMapper() {
        return new LocateCommodityFilterMapper();
    }
}
