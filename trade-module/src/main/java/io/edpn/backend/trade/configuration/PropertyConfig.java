package io.edpn.backend.trade.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration("TradeModulePropertyConfig")
@PropertySources({
        @PropertySource("classpath:trademodule.properties"),
        @PropertySource("classpath:trademodule-local.properties"),
})
public class PropertyConfig {
}
