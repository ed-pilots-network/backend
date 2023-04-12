package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Power;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "power")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class PowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<SystemEntity> postgresSystemEntities;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        PowerEntity that = (PowerEntity) o;
        
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
        public static Optional<PowerEntity> map(Power power) {
            return Optional.ofNullable(power)
                    .map(p -> PowerEntity.builder()
                    .id(p.id())
                    .name(p.name())
                    .build());
        }
        
        public static Optional<Power> map(PowerEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Power.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .build());
        }
    }
    
}
