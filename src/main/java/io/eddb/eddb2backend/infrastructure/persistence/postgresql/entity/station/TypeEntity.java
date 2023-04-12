package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Type;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "type")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(mappedBy = "typeEntity", fetch = FetchType.LAZY)
    private Collection<StationEntity> stationEntities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        TypeEntity that = (TypeEntity) o;
        
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
        public static Optional<TypeEntity> map(Type type) {
            return Optional.ofNullable(type)
                    .map(t -> TypeEntity.builder()
                            .id(t.id())
                            .name(t.name())
                            .build());
        }
        
        public static Optional<Type> map(TypeEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Type.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .build());
        }
    }
    
}
