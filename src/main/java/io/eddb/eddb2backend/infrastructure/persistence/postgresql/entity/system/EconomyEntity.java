package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Economy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "economy")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EconomyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "primaryEconomyEntity")
    private Collection<SystemEntity> systemEntities;
    
    public static class Mapper {
        public static EconomyEntity map(Economy economy) {
            return EconomyEntity.builder()
                    .id(economy.id())
                    .name(economy.name())
                    .build();
        }
        
        public static Economy map(EconomyEntity entity) {
            return Economy.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
