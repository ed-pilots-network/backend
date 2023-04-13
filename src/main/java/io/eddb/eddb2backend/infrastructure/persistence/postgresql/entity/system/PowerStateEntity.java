package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.PowerState;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "powerState")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class PowerStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<SystemEntity> systemEntities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        PowerStateEntity that = (PowerStateEntity) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(name)
                .map(name -> new HashCodeBuilder(17, 49)
                        .append(name)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static Optional<PowerStateEntity> map(PowerState powerState) {
            return Optional.ofNullable(powerState)
                    .map(p -> PowerStateEntity.builder()
                            .id(p.id())
                            .name(p.name())
                            .build());
        }
        
        public static Optional<PowerState> map(PowerStateEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> PowerState.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .build());
        }
    }
    
}
