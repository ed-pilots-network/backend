package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.Government;
import io.eddb.eddb2backend.domain.model.station.LandingPad;
import io.eddb.eddb2backend.domain.model.station.Station;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "station")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Transactional
//TODO: Update to match new domain Station
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public static class Mapper {
        public static StationEntity map(Station station) {
            return StationEntity.builder()
                    .id(station.id())
                    .name(station.name())
                    .build();
        }

        public static Station map(StationEntity entity) {
            return Station.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
}
