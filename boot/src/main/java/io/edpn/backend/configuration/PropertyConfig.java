package io.edpn.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration("BootPropertyConfig")
@PropertySources({
        @PropertySource("classpath:boot.properties"),
})
public class PropertyConfig {
}
