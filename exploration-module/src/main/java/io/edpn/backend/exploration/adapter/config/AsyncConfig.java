package io.edpn.backend.exploration.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ForkJoinPool;

@Configuration("ExplorationAsyncConfig")
@EnableScheduling
public class AsyncConfig {

    @Bean(name = "explorationTaskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10); // todo Set from config
        return taskScheduler;
    }

    @Bean(name = "explorationForkJoinPool")
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(50); // todo set from config
    }
}
