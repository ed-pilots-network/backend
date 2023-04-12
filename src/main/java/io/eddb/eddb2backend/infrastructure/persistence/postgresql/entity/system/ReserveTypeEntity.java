package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.ReserveType;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "reserveType")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class ReserveTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<SystemEntity> systemEntities;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        ReserveTypeEntity that = (ReserveTypeEntity) o;
        
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
        public static Optional<ReserveTypeEntity> map(ReserveType reserveType) {
            return Optional.ofNullable(reserveType)
                    .map(r -> ReserveTypeEntity.builder()
                        .id(r.id())
                        .name(r.name())
                        .build());
        }
        
        public static Optional<ReserveType> map(ReserveTypeEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> ReserveType.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .build());
        }
    }
    
}
