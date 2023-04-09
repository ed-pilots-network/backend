package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity;

import io.eddb.eddb2backend.domain.model.Station;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Optional;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class PostgresStationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private Long id;

    @ToString.Include
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PostgresStationEntity that = (PostgresStationEntity) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return Optional.ofNullable(id)
                .map(id -> new HashCodeBuilder(17, 37)
                        .append(id)
                        .toHashCode())
                .orElse(0);
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
