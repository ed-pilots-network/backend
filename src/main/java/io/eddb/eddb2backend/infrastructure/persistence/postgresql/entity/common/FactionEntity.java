package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common;

import io.eddb.eddb2backend.domain.model.common.Faction;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system.SystemEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

@Entity(name = "faction")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class FactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(mappedBy = "controllingMinorFactionEntity", fetch = FetchType.LAZY)
    private Collection<SystemEntity> systemEntities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        FactionEntity that = (FactionEntity) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(name)
                .map(name -> new HashCodeBuilder(15, 37)
                        .append(name)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static Optional<FactionEntity> map(Faction faction) {
            return Optional.ofNullable(faction)
                    .map(f -> FactionEntity.builder()
                            .id(f.id())
                            .name(f.name())
                            .build());
        }
        
        public static Optional<Faction> map(FactionEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Faction.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .build());
        }
    }
    
}
