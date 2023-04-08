package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.ReserveType;
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
public class PostgresReserveTypeEntity {
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
        public static PostgresReserveTypeEntity map(ReserveType reserveType) {
            return PostgresReserveTypeEntity.builder()
                    .id(reserveType.id())
                    .name(reserveType.name())
                    .build();
        }
        
        public static ReserveType map(PostgresReserveTypeEntity entity) {
            return ReserveType.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
