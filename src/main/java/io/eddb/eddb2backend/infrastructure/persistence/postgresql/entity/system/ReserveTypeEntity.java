package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.ReserveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "reserveType")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany
    private Collection<SystemEntity> systemEntities;
    
    public static class Mapper {
        public static ReserveTypeEntity map(ReserveType reserveType) {
            return ReserveTypeEntity.builder()
                    .id(reserveType.id())
                    .name(reserveType.name())
                    .build();
        }
        
        public static ReserveType map(ReserveTypeEntity entity) {
            return ReserveType.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
