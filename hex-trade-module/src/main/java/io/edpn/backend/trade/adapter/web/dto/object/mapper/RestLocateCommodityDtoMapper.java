package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;

public class RestLocateCommodityDtoMapper implements LocateCommodityDtoMapper {
    
    private InnerStationDtoMapper innerStationDtoMapper;
    
    @Override
    public LocateCommodityDto map(LocateCommodity locateCommodity) {
        return new RestLocateCommodityDto(
                locateCommodity.validatedCommodity().displayName(),
                innerStationDtoMapper.map(locateCommodity.station()),
                locateCommodity.system().name(),
                locateCommodity.pricesUpdatedAt(),
                locateCommodity.supply(),
                locateCommodity.demand(),
                locateCommodity.buyPrice(),
                locateCommodity.sellPrice(),
                locateCommodity.distance()
                
        );
    }
    
    private static class InnerStationDtoMapper implements StationDtoMapper{
        
        @Override
        public RestLocateCommodityDto.InnerStationDto map(Station station) {
            return RestLocateCommodityDto.InnerStationDto.builder()
                    .name(station.name())
                    .arrivalDistance(station.arrivalDistance())
                    .maxLandingPadSize(String.valueOf(station.maxLandingPadSize()))
                    .fleetCarrier(station.fleetCarrier())
                    .requireOdyssey(station.requireOdyssey())
                    .planetary(station.planetary())
                    .build();
        }
    }
}
