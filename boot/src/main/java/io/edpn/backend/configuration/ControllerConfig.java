package io.edpn.backend.configuration;

import io.edpn.backend.application.controller.CommodityFinderController;
import io.edpn.backend.commodityfinder.application.controller.BestCommodityPriceController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public CommodityFinderController commodityFinderController(BestCommodityPriceController bestCommodityPriceController) {
        return new CommodityFinderController(bestCommodityPriceController);
    }
}
