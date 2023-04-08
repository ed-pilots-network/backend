package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system;

import io.eddb.eddb2backend.domain.model.system.Coordinate;
import io.eddb.eddb2backend.domain.model.system.System;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostgresSystemEntity {
    
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
    private PostgresGovernmentEntity governmentEntity;
    @ManyToOne
    private PostgresAllegianceEntity allegianceEntity;
    @ManyToOne
    private PostgresSecurityEntity securityEntity;
    @ManyToOne
    private PostgresEconomyEntity primaryEconomyEntity;
    @ManyToOne
    private PostgresPowerEntity powerEntity;
    @ManyToOne
    private PostgresPowerStateEntity powerStateEntity;
    @ManyToOne
    private PostgresFactionEntity controllingMinorFactionEntity;
    @ManyToOne
    private PostgresReserveTypeEntity reserveTypeEntity;
    
    public static class Mapper {
        public static PostgresSystemEntity map(System system){
            return PostgresSystemEntity.builder()
                    .id(system.id())
                    .name(system.name())
                    .population(system.population())
                    .needsPermit(system.needsPermit())
                    .lastUpdated(system.lastUpdated())
                    .edSystemAddress(system.edSystemAddress())
                    .coordinate(system.coordinate())
                    .governmentEntity(PostgresGovernmentEntity.Mapper.map(system.government()))
                    .allegianceEntity(PostgresAllegianceEntity.Mapper.map(system.allegiance()))
                    .securityEntity(PostgresSecurityEntity.Mapper.map(system.security()))
                    .primaryEconomyEntity(PostgresEconomyEntity.Mapper.map(system.primaryEconomy()))
                    .powerEntity(PostgresPowerEntity.Mapper.map(system.power()))
                    .powerStateEntity(PostgresPowerStateEntity.Mapper.map(system.powerState()))
                    .controllingMinorFactionEntity(PostgresFactionEntity.Mapper.map(system.controllingMinorFaction()))
                    .reserveTypeEntity(PostgresReserveTypeEntity.Mapper.map(system.reserveType()))
                    .build();
            
        }
        
        public static System map(PostgresSystemEntity entity){
            return System.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .population(entity.getPopulation())
                    .needsPermit(entity.isNeedsPermit())
                    .lastUpdated(entity.getLastUpdated())
                    .edSystemAddress(entity.getEdSystemAddress())
                    .coordinate(entity.getCoordinate())
                    .government(PostgresGovernmentEntity.Mapper.map(entity.getGovernmentEntity()))
                    .allegiance(PostgresAllegianceEntity.Mapper.map(entity.getAllegianceEntity()))
                    .security(PostgresSecurityEntity.Mapper.map(entity.getSecurityEntity()))
                    .primaryEconomy(PostgresEconomyEntity.Mapper.map(entity.getPrimaryEconomyEntity()))
                    .power(PostgresPowerEntity.Mapper.map(entity.getPowerEntity()))
                    .powerState(PostgresPowerStateEntity.Mapper.map(entity.getPowerStateEntity()))
                    .controllingMinorFaction(PostgresFactionEntity.Mapper.map(entity.getControllingMinorFactionEntity()))
                    .reserveType(PostgresReserveTypeEntity.Mapper.map(entity.getReserveTypeEntity()))
                    .build();
        }
    }
}
