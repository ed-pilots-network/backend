package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Allegiance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostgresAllegianceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany
    private PostgresSystemEntity postgresSystemEntity;
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public static class Mapper {
        public static PostgresAllegianceEntity map(Allegiance allegiance) {
            return PostgresAllegianceEntity.builder()
                    .id(allegiance.id())
                    .name(allegiance.name())
                    .build();
        }
        
        public static Allegiance map(PostgresAllegianceEntity entity) {
            return Allegiance.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
