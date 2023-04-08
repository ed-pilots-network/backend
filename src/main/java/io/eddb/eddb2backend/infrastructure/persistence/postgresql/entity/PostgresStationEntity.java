package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity;

import io.eddb.eddb2backend.domain.model.station.Station;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostgresStationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static class Mapper {
        public static PostgresStationEntity map(Station station) {
            return PostgresStationEntity.builder()
                    .id(station.id())
                    .name(station.name())
                    .build();
        }

        public static Station map(PostgresStationEntity entity) {
            return Station.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
}
