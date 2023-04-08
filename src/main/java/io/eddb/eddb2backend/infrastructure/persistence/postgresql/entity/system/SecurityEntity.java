package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Security;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity(name = "security")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany
    private Collection<SystemEntity> systemEntities;
    
    public static class Mapper {
        public static SecurityEntity map(Security security) {
            return SecurityEntity.builder()
                    .id(security.id())
                    .name(security.name())
                    .build();
        }
        
        public static Security map(SecurityEntity entity) {
            return Security.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
