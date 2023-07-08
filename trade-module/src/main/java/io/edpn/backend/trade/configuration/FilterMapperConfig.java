package io.edpn.backend.trade.configuration;

import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.FindCommodityFilterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleEntityMapperConfig")
public class FilterMapperConfig {

    @Bean
    public FindCommodityFilterMapper findCommodityFilterMapper() {
        return new FindCommodityFilterMapper();
    }
}
