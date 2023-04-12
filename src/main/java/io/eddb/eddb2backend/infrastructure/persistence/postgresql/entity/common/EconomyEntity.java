package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common;

import io.eddb.eddb2backend.domain.model.common.Economy;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system.SystemEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "economy")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class EconomyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(mappedBy = "primaryEconomyEntity", fetch = FetchType.LAZY)
    private Collection<SystemEntity> systemEntities;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        EconomyEntity that = (EconomyEntity) o;
        
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
        public static Optional<EconomyEntity> map(Economy economy) {
            return Optional.ofNullable(economy)
                    .map(e ->  EconomyEntity.builder()
                    .id(e.id().describeConstable().orElse(null))
                    .name(e.name().describeConstable().orElse(null))
                    .build());
        }
        
        public static Optional<Economy> map(EconomyEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Economy.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .build());
        }
    }
    
}
