package io.edpn.backend.modulith.commodityfinder.application.service;

import io.edpn.backend.modulith.commodityfinder.application.mappers.persistence.BestCommodityPriceMapper;
import io.edpn.backend.modulith.commodityfinder.domain.entity.BestCommodityPrice;
import io.edpn.backend.modulith.commodityfinder.domain.repository.MarketDatumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class MarketDatumService {

    private final MarketDatumRepository marketDatumRepository;
    private final BestCommodityPriceMapper bestCommodityPriceMapper;


    public List<BestCommodityPrice> findAll() {
        return bestCommodityPriceMapper.map( marketDatumRepository.findAllBestCommodityPrices());
    }
}
