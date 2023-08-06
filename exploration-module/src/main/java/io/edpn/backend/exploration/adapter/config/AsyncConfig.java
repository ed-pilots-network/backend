package io.edpn.backend.exploration.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration("ExplorationAsyncConfig")
@EnableScheduling
public class AsyncConfig {

    @Bean(name = "explorationTaskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10); // todo Set from config
        return taskScheduler;
    }

    @Bean(name = "explorationThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);// todo Set from config
        taskExecutor.setMaxPoolSize(50);// todo Set from config
        taskExecutor.setQueueCapacity(500);// todo Set from config
        taskExecutor.initialize();
        return taskExecutor;
    }
}
