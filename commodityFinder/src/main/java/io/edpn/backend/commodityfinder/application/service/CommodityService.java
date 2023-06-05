package io.edpn.backend.commodityfinder.application.service;

import io.edpn.backend.commodityfinder.application.mappers.persistence.CommodityMapper;
import io.edpn.backend.commodityfinder.domain.entity.Commodity;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
public class CommodityService {

    private final CommodityRepository commodityRepository;
    private final CommodityMapper commodityMapper;

    public Commodity getOrCreateByName(String name) {
        return commodityMapper.map(commodityRepository.findOrCreateByName(name));
    }
}
