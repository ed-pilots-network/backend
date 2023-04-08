package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.PowerState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "powerState")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PowerStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany
    private Collection<SystemEntity> systemEntities;
    
    public static class Mapper {
        public static PowerStateEntity map(PowerState powerState) {
            return PowerStateEntity.builder()
                    .id(powerState.id())
                    .name(powerState.name())
                    .build();
        }
        
        public static PowerState map(PowerStateEntity entity) {
            return PowerState.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
