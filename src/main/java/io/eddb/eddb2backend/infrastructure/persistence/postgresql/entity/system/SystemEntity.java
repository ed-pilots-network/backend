package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Coordinate;
import io.eddb.eddb2backend.domain.model.system.System;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.body.BodyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.AllegianceEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.EconomyEntity;
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
        public static SystemEntity map(System system){
            return SystemEntity.builder()
                    .id(system.id())
                    .name(system.name())
                    .population(system.population())
                    .needsPermit(system.needsPermit())
                    .lastUpdated(system.lastUpdated())
                    .edSystemAddress(system.edSystemAddress())
                    .coordinate(system.coordinate())
                    .governmentEntity(GovernmentEntity.Mapper.map(system.government()))
                    .allegianceEntity(AllegianceEntity.Mapper.map(system.allegiance()))
                    .securityEntity(SecurityEntity.Mapper.map(system.security()))
                    .primaryEconomyEntity(EconomyEntity.Mapper.map(system.primaryEconomy()))
                    .powerEntity(PowerEntity.Mapper.map(system.power()))
                    .powerStateEntity(PowerStateEntity.Mapper.map(system.powerState()))
                    .controllingMinorFactionEntity(FactionEntity.Mapper.map(system.controllingMinorFaction()))
                    .reserveTypeEntity(ReserveTypeEntity.Mapper.map(system.reserveType()))
                    .bodies(system.bodies().stream().map(BodyEntity.Mapper::map).collect(Collectors.toSet()))
                    .build();
            
        }
        
        public static System map(SystemEntity entity){
            return System.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .population(entity.getPopulation())
                    .needsPermit(entity.isNeedsPermit())
                    .lastUpdated(entity.getLastUpdated())
                    .edSystemAddress(entity.getEdSystemAddress())
                    .coordinate(entity.getCoordinate())
                    .government(GovernmentEntity.Mapper.map(entity.getGovernmentEntity()))
                    .allegiance(AllegianceEntity.Mapper.map(entity.getAllegianceEntity()))
                    .security(SecurityEntity.Mapper.map(entity.getSecurityEntity()))
                    .primaryEconomy(EconomyEntity.Mapper.map(entity.getPrimaryEconomyEntity()))
                    .power(PowerEntity.Mapper.map(entity.getPowerEntity()))
                    .powerState(PowerStateEntity.Mapper.map(entity.getPowerStateEntity()))
                    .controllingMinorFaction(FactionEntity.Mapper.map(entity.getControllingMinorFactionEntity()))
                    .reserveType(ReserveTypeEntity.Mapper.map(entity.getReserveTypeEntity()))
                    .bodies(entity.getBodies().stream().map(BodyEntity.Mapper::map).collect(Collectors.toSet()))
                    .build();
        }
    }
}
