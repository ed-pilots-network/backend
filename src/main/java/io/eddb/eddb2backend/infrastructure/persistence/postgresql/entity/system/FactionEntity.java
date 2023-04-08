package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.Faction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "faction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "controllingMinorFactionEntity")
    private Collection<SystemEntity> systemEntities;
    
    public static class Mapper {
        public static FactionEntity map(Faction faction) {
            return FactionEntity.builder()
                    .id(faction.id())
                    .name(faction.name())
                    .build();
        }
        
        public static Faction map(FactionEntity entity) {
            return Faction.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
