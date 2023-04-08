package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Government;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostgresGovernmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany
    private Collection<PostgresSystemEntity> postgresSystemEntities;
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public static class Mapper {
        public static PostgresGovernmentEntity map(Government government) {
            return PostgresGovernmentEntity.builder()
                    .id(government.id())
                    .name(government.name())
                    .build();
        }
        
        public static Government map(PostgresGovernmentEntity entity) {
            return Government.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
