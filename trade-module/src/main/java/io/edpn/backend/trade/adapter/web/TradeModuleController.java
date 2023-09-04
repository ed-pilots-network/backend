package io.edpn.backend.trade.adapter.web;

import io.edpn.backend.trade.adapter.web.dto.filter.RestFindCommodityFilterDto;
import io.edpn.backend.trade.adapter.web.dto.filter.RestLocateCommodityFilterDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestCommodityMarketInfoDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestPageInfoDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestPagedResultDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestValidatedCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.PagedResultDto;
import io.edpn.backend.trade.application.port.incomming.commoditymarketinfo.GetFullCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNameUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@RequestMapping("/api/v1/trade")
public class TradeModuleController {

    private final FindAllValidatedCommodityUseCase findAllValidatedCommodityUseCase;
    private final FindValidatedCommodityByNameUseCase findValidatedCommodityByNameUseCase;
    private final FindValidatedCommodityByFilterUseCase findValidatedCommodityByFilterUseCase;
    private final LocateCommodityUseCase locateCommodityUseCase;
    private final GetFullCommodityMarketInfoUseCase getFullCommodityMarketInfoUseCase;


    @GetMapping("/commodity")
    public List<RestValidatedCommodityDto> findAll() {
        return findAllValidatedCommodityUseCase.findAll()
                .stream()
                .map(x -> (RestValidatedCommodityDto) x)
                .toList();
    }

    @GetMapping("/commodity/filter")
    public List<RestValidatedCommodityDto> findByFilter(RestFindCommodityFilterDto findCommodityRequest) {
        return findValidatedCommodityByFilterUseCase
                .findByFilter(findCommodityRequest)
                .stream()
                .map(x -> (RestValidatedCommodityDto) x)
                .toList();
    }

    @GetMapping("/commodity/{displayName}")
    public Optional<RestValidatedCommodityDto> findByName(@PathVariable String displayName) {
        return findValidatedCommodityByNameUseCase
                .findByName(displayName)
                .map(x -> (RestValidatedCommodityDto) x);
    }
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RestPagedResultDto.class), mediaType = "application/json")})
    })
    @GetMapping("/locate-commodity")
    public PagedResultDto<RestLocateCommodityDto> locateCommodityWithFilters(RestLocateCommodityFilterDto locateCommodityFilterDto) {
        BiFunction<List<RestLocateCommodityDto>, RestPageInfoDto, RestPagedResultDto<RestLocateCommodityDto>> constructor = RestPagedResultDto::new;
        return locateCommodityUseCase
                .locateCommodityOrderByDistance(locateCommodityFilterDto, constructor);
    }

    @GetMapping("/best-price")
    List<RestCommodityMarketInfoDto> fullMarketInfo() {
        return getFullCommodityMarketInfoUseCase
                .findAll()
                .stream()
                .map(x -> (RestCommodityMarketInfoDto) x)
                .toList();
    }
}
