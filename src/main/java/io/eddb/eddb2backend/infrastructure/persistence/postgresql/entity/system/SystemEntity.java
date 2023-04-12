package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Coordinate;
import io.eddb.eddb2backend.domain.model.system.System;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.body.BodyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.AllegianceEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.EconomyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.FactionEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.GovernmentEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity(name = "system")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class SystemEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    private Long population;
    private boolean needsPermit;
    private LocalDateTime lastUpdated;
    private Long edSystemAddress;
    
    @Embedded
    private Coordinate coordinate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "government_id")
    private GovernmentEntity governmentEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allegiance_id")
    private AllegianceEntity allegianceEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_id")
    private SecurityEntity securityEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_economy_id")
    private EconomyEntity primaryEconomyEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_id")
    private PowerEntity powerEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_state_id")
    private PowerStateEntity powerStateEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "controlling_minor_faction_id")
    private FactionEntity controllingMinorFactionEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_type_id")
    private ReserveTypeEntity reserveTypeEntity;
    
    @OneToMany(mappedBy = "systemEntity")
    private Collection<BodyEntity> bodies;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return  true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        SystemEntity that = (SystemEntity) o;
        
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
        public static Optional<SystemEntity> map(System system){
            return Optional.ofNullable(system)
                    .map(s -> SystemEntity.builder()
                    .id(s.id())
                    .name(s.name())
                    .population(s.population())
                    .needsPermit(s.needsPermit())
                    .lastUpdated(s.lastUpdated())
                    .edSystemAddress(s.edSystemAddress())
                    .coordinate(s.coordinate())
                    .governmentEntity(GovernmentEntity.Mapper.map(s.government()).orElse(null))
                    .allegianceEntity(AllegianceEntity.Mapper.map(s.allegiance()).orElse(null))
                    .securityEntity(SecurityEntity.Mapper.map(s.security()).orElse(null))
                    .primaryEconomyEntity(EconomyEntity.Mapper.map(s.primaryEconomy()).orElse(null))
                    .powerEntity(PowerEntity.Mapper.map(s.power()).orElse(null))
                    .powerStateEntity(PowerStateEntity.Mapper.map(s.powerState()).orElse(null))
                    .controllingMinorFactionEntity(FactionEntity.Mapper.map(s.controllingMinorFaction()).orElse(null))
                    .reserveTypeEntity(ReserveTypeEntity.Mapper.map(s.reserveType()).orElse(null))
                    .bodies(
                            Optional.ofNullable(s.bodies())
                                    .map(b ->b.stream()
                                            .map(BodyEntity.Mapper::map)
                                            .map(entity -> entity.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .build());
            
        }
        
        public static Optional<System> map(SystemEntity entity){
            return Optional.ofNullable(entity)
                    .map(e -> System.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .population(e.getPopulation())
                    .needsPermit(e.isNeedsPermit())
                    .lastUpdated(e.getLastUpdated())
                    .edSystemAddress(e.getEdSystemAddress())
                    .coordinate(e.getCoordinate())
                    .government(GovernmentEntity.Mapper.map(e.getGovernmentEntity()).orElse(null))
                    .allegiance(AllegianceEntity.Mapper.map(e.getAllegianceEntity()).orElse(null))
                    .security(SecurityEntity.Mapper.map(e.getSecurityEntity()).orElse(null))
                    .primaryEconomy(EconomyEntity.Mapper.map(e.getPrimaryEconomyEntity()).orElse(null))
                    .power(PowerEntity.Mapper.map(e.getPowerEntity()).orElse(null))
                    .powerState(PowerStateEntity.Mapper.map(e.getPowerStateEntity()).orElse(null))
                    .controllingMinorFaction(FactionEntity.Mapper.map(e.getControllingMinorFactionEntity()).orElse(null))
                    .reserveType(ReserveTypeEntity.Mapper.map(e.getReserveTypeEntity()).orElse(null))
                    .bodies(
                            Optional.ofNullable(e.getBodies())
                                    .map(b -> b.stream()
                                            .map(BodyEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .build());
        }
    }
}
