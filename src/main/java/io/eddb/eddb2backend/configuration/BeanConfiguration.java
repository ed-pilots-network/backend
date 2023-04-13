package io.eddb.eddb2backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.application.service.GetStationService;
import io.eddb.eddb2backend.application.usecase.GetStationUsecase;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.infrastructure.adapter.StationRepositoryAdapter;
import io.eddb.eddb2backend.infrastructure.eddn.EddnMessageHandler;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.PostgresStationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public StationRepository stationRepository(PostgresStationRepository postgresqlPostgresStationRepository) {
        return new StationRepositoryAdapter(postgresqlPostgresStationRepository);
    }

    @Bean
    public GetStationService getStationService(io.eddb.eddb2backend.domain.repository.StationRepository stationRepository) {
        return new GetStationService(stationRepository);
    }

    @Bean
    public GetStationUsecase getStationUsecase(GetStationService getStationService) {
        return getStationService;
    }

    @Bean
    public EddnMessageHandler eddnMessageHandler(@Qualifier("eddnTaskExecutor") TaskExecutor taskExecutor, @Qualifier("eddnRetryTemplate")RetryTemplate retryTemplate,  ObjectMapper objectMapper) {
        return new EddnMessageHandler(taskExecutor, retryTemplate, objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
