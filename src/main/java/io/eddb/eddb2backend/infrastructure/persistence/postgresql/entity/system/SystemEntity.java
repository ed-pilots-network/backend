package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Coordinate;
import io.eddb.eddb2backend.domain.model.system.System;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity(name = "system")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private Long population;
    private boolean needsPermit;
    private Date lastUpdated;
    private Long edSystemAddress;
    
    @Embedded
    private Coordinate coordinate;
    @ManyToOne
    @JoinColumn(name = "government_id")
    private GovernmentEntity governmentEntity;
    @ManyToOne
    @JoinColumn(name = "allegiance_id")
    private AllegianceEntity allegianceEntity;
    @ManyToOne
    @JoinColumn(name = "security_id")
    private SecurityEntity securityEntity;
    @ManyToOne
    @JoinColumn(name = "primary_economy_id")
    private EconomyEntity primaryEconomyEntity;
    @ManyToOne
    @JoinColumn(name = "power_id")
    private PowerEntity powerEntity;
    @ManyToOne
    @JoinColumn(name = "power_state_id")
    private PowerStateEntity powerStateEntity;
    @ManyToOne
    @JoinColumn(name = "controlling_minor_faction_id")
    private FactionEntity controllingMinorFactionEntity;
    @ManyToOne
    @JoinColumn(name = "reserve_type_id")
    private ReserveTypeEntity reserveTypeEntity;
    
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
                    .build();
        }
    }
}
