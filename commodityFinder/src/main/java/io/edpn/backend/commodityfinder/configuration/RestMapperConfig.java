package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.mappers.BestCommodityPriceResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("CommodityFinderRestMapperConfig")
public class RestMapperConfig {

    @Bean
    public BestCommodityPriceResponseMapper bestCommodityPriceResponseMapper() {
        return new BestCommodityPriceResponseMapper();
    }
}
