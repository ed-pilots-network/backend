package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Power;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "power")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany
    private Collection<SystemEntity> postgresSystemEntities;
    
    public static class Mapper {
        public static PowerEntity map(Power power) {
            return PowerEntity.builder()
                    .id(power.id())
                    .name(power.name())
                    .build();
        }
        
        public static Power map(PowerEntity entity) {
            return Power.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
