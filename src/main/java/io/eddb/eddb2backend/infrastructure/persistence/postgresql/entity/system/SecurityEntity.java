package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Security;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "security")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class SecurityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<SystemEntity> systemEntities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        SecurityEntity that = (SecurityEntity) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(name)
                .map(name -> new HashCodeBuilder(13, 73)
                        .append(name)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static Optional<SecurityEntity> map(Security security) {
            return Optional.ofNullable(security)
                    .map(s -> SecurityEntity.builder()
                            .id(s.id())
                            .name(s.name())
                            .build());
        }
        
        public static Optional<Security> map(SecurityEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Security.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .build());
        }
    }
    
}
