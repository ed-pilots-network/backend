package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Module;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "module")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @ManyToMany(mappedBy = "moduleEntities", fetch = FetchType.LAZY)
    private Collection<StationEntity> stationEntities;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        ModuleEntity that = (ModuleEntity) o;
        
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
        public static Optional<ModuleEntity> map(Module module) {
            return Optional.ofNullable(module)
                    .map(m -> ModuleEntity.builder()
                    .id(m.id())
                    .name(m.name())
                    .build());
        }
        
        public static Optional<Module> map(ModuleEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Module.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .build());
        }
    }
    
}
