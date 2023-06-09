package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.util.AutoCloseableSemaphore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SemaphoreConfig {

    @Bean
    public Map<String, AutoCloseableSemaphore> semaphores() {
        return Map.of(
                "system", new AutoCloseableSemaphore(1),
                "station", new AutoCloseableSemaphore(1),
                "commodity", new AutoCloseableSemaphore(1),
                "marketDatum", new AutoCloseableSemaphore(1));
    }
}
