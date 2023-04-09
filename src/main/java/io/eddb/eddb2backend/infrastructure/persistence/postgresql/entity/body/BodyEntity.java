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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id")
    private SystemEntity systemEntity;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        BodyEntity that = (BodyEntity) o;
        
        return new EqualsBuilder().append(id, that.id).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(id)
                .map(id -> new HashCodeBuilder(17, 37)
                        .append(id)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static BodyEntity map(Body body) {
            return BodyEntity.builder()
                    .id(body.id())
                    .name(body.name())
                    .systemEntity(SystemEntity.Mapper.map(body.system()))
                    .build();
        }
        
        public static Body map(BodyEntity entity) {
            return Body.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .system(SystemEntity.Mapper.map(entity.getSystemEntity()))
                    .build();
        }
    }
}
