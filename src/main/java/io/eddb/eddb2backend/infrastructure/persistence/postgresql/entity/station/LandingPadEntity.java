package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.LandingPad;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "landingPad")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class LandingPadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private char size;
    
    @OneToMany(mappedBy = "maxSizeLandingPadEntity", fetch = FetchType.LAZY)
    private Collection<StationEntity> stationEntities;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        LandingPadEntity that = (LandingPadEntity) o;
        
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
        public static Optional<LandingPadEntity> map(LandingPad landingPad) {
            return Optional.ofNullable(landingPad)
                    .map(lp -> LandingPadEntity.builder()
                    .id(lp.id())
                    .size(lp.size())
                    .build());
        }
        
        public static Optional<LandingPad> map(LandingPadEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> LandingPad.builder()
                    .id(e.getId())
                    .size(e.getSize())
                    .build());
        }
    }
    
}
