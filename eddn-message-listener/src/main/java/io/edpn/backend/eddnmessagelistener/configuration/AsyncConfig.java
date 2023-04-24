package io.edpn.backend.eddnmessagelistener.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    @Qualifier("eddnTaskExecutor")
    @ConfigurationProperties(prefix = "eddn.zeromq.executor")
    public TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }


    @Bean
    @Qualifier("eddnRetryTemplate")
    public RetryTemplate retryTemplate(@Value("${eddn.zeromq.executor.retry.initalinterval}") int initialInterval,
                                       @Value("${eddn.zeromq.executor.retry.intervalmultiplier}") double intervalMultiplier,
                                       @Value("${eddn.zeromq.executor.retry.maxinterval}") int maxInterval,
                                       @Value("${eddn.zeromq.executor.retry.maxattempts}") int maxAttempts) {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initialInterval);
        backOffPolicy.setMultiplier(intervalMultiplier);
        backOffPolicy.setMaxInterval(maxInterval);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }
}
