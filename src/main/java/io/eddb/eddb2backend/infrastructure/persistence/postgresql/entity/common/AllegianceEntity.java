package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common;

import io.eddb.eddb2backend.domain.model.common.Allegiance;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system.SystemEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "allegiance")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class AllegianceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(mappedBy = "allegianceEntity", fetch = FetchType.LAZY)
    private Collection<SystemEntity> systemEntities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        AllegianceEntity that = (AllegianceEntity) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(name)
                .map(name -> new HashCodeBuilder(3, 31)
                        .append(name)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static Optional<AllegianceEntity> map(Allegiance allegiance) {
            return Optional.ofNullable(allegiance)
                    .map(a -> AllegianceEntity.builder()
                            .id(a.id())
                            .name(a.name())
                            .build());
        }
        
        public static Optional<Allegiance> map(AllegianceEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Allegiance.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .build());
        }
    }
    
}
