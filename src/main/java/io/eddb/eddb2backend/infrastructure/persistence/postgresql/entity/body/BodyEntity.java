package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.body;

import io.eddb.eddb2backend.domain.model.body.Body;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system.SystemEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Optional;

@Entity(name = "body")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class BodyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "system_id")
    private SystemEntity systemEntity;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        BodyEntity that = (BodyEntity) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(name)
                .map(name -> new HashCodeBuilder(1, 11)
                        .append(name)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static Optional<BodyEntity> map(Body body) {
            return Optional.ofNullable(body)
                    .map(b -> BodyEntity.builder()
                            .id(b.id())
                            .name(b.name())
                            .systemEntity(SystemEntity.Mapper.map(b.system()).orElse(null))
                            .build());
        }
        
        public static Optional<Body> map(BodyEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Body.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .system(SystemEntity.Mapper.map(e.getSystemEntity()).orElse(null))
                            .build());
        }
    }
}
