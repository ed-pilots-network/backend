package io.edpn.backend.user.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration("UserModulePropertyConfig")
@PropertySources({
        @PropertySource("classpath:usermodule.properties"),
        @PropertySource("classpath:usermodule-local.properties"),
})
public class PropertyConfig {
}
