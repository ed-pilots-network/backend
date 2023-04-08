package io.eddb.eddb2backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
public class AsyncConfig {

    @Bean
    @ConfigurationProperties(prefix = "eddn.zeromq.executor")
    public TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
