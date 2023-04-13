package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Ship;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "ship")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class ShipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @ManyToMany(mappedBy = "sellingShipEntities", fetch = FetchType.LAZY)
    private Collection<StationEntity> stationEntities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        ShipEntity that = (ShipEntity) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(name)
                .map(name -> new HashCodeBuilder(15, 29)
                        .append(name)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static Optional<ShipEntity> map(Ship ship) {
            return Optional.ofNullable(ship)
                    .map(s -> ShipEntity.builder()
                            .id(s.id())
                            .name(s.name())
                            .build());
        }
        
        public static Optional<Ship> map(ShipEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Ship.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .build());
        }
    }
    
}
