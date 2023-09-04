package io.edpn.backend.trade.application.port.incomming.locatecommodity;

import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.PageInfoDto;
import io.edpn.backend.trade.application.dto.web.object.PagedResultDto;

import java.util.List;
import java.util.function.BiFunction;

public interface LocateCommodityUseCase {

    <T extends LocateCommodityDto, U extends PagedResultDto<T>, R extends PageInfoDto> PagedResultDto<T> locateCommodityOrderByDistance(LocateCommodityFilterDto locateCommodityFilterDto, BiFunction<List<T>, R, U> pagedResultConstructor);

}
