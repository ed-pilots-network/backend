package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common;

import io.eddb.eddb2backend.domain.model.common.Government;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system.SystemEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "government")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class GovernmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(mappedBy = "governmentEntity", fetch = FetchType.LAZY)
    private Collection<SystemEntity> postgresSystemEntities;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        GovernmentEntity that = (GovernmentEntity) o;
        
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
        public static Optional<GovernmentEntity> map(Government government) {
            return Optional.ofNullable(government)
                    .map(g -> GovernmentEntity.builder()
                    .id(g.id().describeConstable().orElse(null))
                    .name(g.name().describeConstable().orElse(null))
                    .build());
        }
        
        public static Optional<Government> map(GovernmentEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Government.builder()
                    .id(e.getId().describeConstable().orElse(null))
                    .name(e.getName().describeConstable().orElse(null))
                    .build());
        }
    }
    
}
