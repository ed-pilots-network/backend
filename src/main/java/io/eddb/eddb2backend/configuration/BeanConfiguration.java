package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.application.service.GetStationService;
import io.eddb.eddb2backend.application.usecase.GetStationUsecase;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.infrastructure.adapter.StationRepositoryAdapter;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.PostgresqlStationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public StationRepository stationRepository(PostgresqlStationRepository postgresqlStationRepository) {
        return new StationRepositoryAdapter(postgresqlStationRepository);
    }

    @Bean
    public GetStationService getStationService(StationRepository stationRepository) {
        return new GetStationService(stationRepository);
    }

    @Bean
    public GetStationUsecase getStationUsecase(GetStationService getStationService) {
        return getStationService;
    }
}
