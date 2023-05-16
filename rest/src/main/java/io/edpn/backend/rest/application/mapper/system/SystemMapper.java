package io.edpn.backend.rest.application.mapper.system;

import io.edpn.backend.rest.application.dto.system.FindSystemResponse;
import io.edpn.backend.rest.domain.model.system.System;

public class SystemMapper{
        public static FindSystemResponse map(System system) {
            return FindSystemResponse.builder()
                    .id(system.getId())
                    .name(system.getName())
                    .coordinate(system.getCoordinate())
                    .population(system.getPopulation())
                    .government(system.getGovernment())
                    .allegiance(system.getAllegiance())
                    .security(system.getSecurity())
                    .primaryEconomy(system.getPrimaryEconomy())
                    .power(system.getPower())
                    .powerState(system.getPowerState())
                    .needsPermit(system.isNeedsPermit())
                    .lastUpdated(system.getLastUpdated())
                    .controllingMinorFaction(system.getControllingMinorFaction())
                    .reserveType(system.getReserveType())
                    .eliteId(system.getEliteId())
                    .build();
        }
        
        public static System map(FindSystemResponse findSystemResponse) {
            return System.builder()
                    .id(findSystemResponse.getId())
                    .name(findSystemResponse.getName())
                    .coordinate(findSystemResponse.getCoordinate())
                    .population(findSystemResponse.getPopulation())
                    .government(findSystemResponse.getGovernment())
                    .allegiance(findSystemResponse.getAllegiance())
                    .security(findSystemResponse.getSecurity())
                    .primaryEconomy(findSystemResponse.getPrimaryEconomy())
                    .power(findSystemResponse.getPower())
                    .powerState(findSystemResponse.getPowerState())
                    .needsPermit(findSystemResponse.isNeedsPermit())
                    .lastUpdated(findSystemResponse.getLastUpdated())
                    .controllingMinorFaction(findSystemResponse.getControllingMinorFaction())
                    .reserveType(findSystemResponse.getReserveType())
                    .eliteId(findSystemResponse.getEliteId())
                    .build();
        }
}
