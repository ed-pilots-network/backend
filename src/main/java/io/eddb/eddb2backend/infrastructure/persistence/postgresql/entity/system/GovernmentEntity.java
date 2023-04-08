package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Government;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "government")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GovernmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "governmentEntity")
    private Collection<SystemEntity> postgresSystemEntities;
    
    public static class Mapper {
        public static GovernmentEntity map(Government government) {
            return GovernmentEntity.builder()
                    .id(government.id())
                    .name(government.name())
                    .build();
        }
        
        public static Government map(GovernmentEntity entity) {
            return Government.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
