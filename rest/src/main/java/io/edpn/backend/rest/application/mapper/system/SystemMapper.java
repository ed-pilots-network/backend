package io.edpn.backend.rest.application.mapper.system;

import io.edpn.backend.rest.application.dto.system.GetSystemResponse;
import io.edpn.backend.rest.domain.model.system.System;

public class SystemMapper{
        public static GetSystemResponse map(System system) {
            return GetSystemResponse.builder()
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
        
        public static System map(GetSystemResponse getSystemResponse) {
            return System.builder()
                    .id(getSystemResponse.getId())
                    .name(getSystemResponse.getName())
                    .coordinate(getSystemResponse.getCoordinate())
                    .population(getSystemResponse.getPopulation())
                    .government(getSystemResponse.getGovernment())
                    .allegiance(getSystemResponse.getAllegiance())
                    .security(getSystemResponse.getSecurity())
                    .primaryEconomy(getSystemResponse.getPrimaryEconomy())
                    .power(getSystemResponse.getPower())
                    .powerState(getSystemResponse.getPowerState())
                    .needsPermit(getSystemResponse.isNeedsPermit())
                    .lastUpdated(getSystemResponse.getLastUpdated())
                    .controllingMinorFaction(getSystemResponse.getControllingMinorFaction())
                    .reserveType(getSystemResponse.getReserveType())
                    .eliteId(getSystemResponse.getEliteId())
                    .build();
        }
}
