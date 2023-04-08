package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Allegiance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "allegiance")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllegianceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "allegianceEntity")
    private Collection<SystemEntity> systemEntities;
    
    public static class Mapper {
        public static AllegianceEntity map(Allegiance allegiance) {
            return AllegianceEntity.builder()
                    .id(allegiance.id())
                    .name(allegiance.name())
                    .build();
        }
        
        public static Allegiance map(AllegianceEntity entity) {
            return Allegiance.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
