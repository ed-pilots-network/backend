package io.edpn.backend.modulith.commodityfinder.application.service;

import io.edpn.backend.modulith.commodityfinder.application.mappers.persistence.CommodityMapper;
import io.edpn.backend.modulith.commodityfinder.domain.entity.Commodity;
import io.edpn.backend.modulith.commodityfinder.domain.repository.CommodityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommodityService {

    private final CommodityRepository commodityRepository;
    private final CommodityMapper commodityMapper;

    public Commodity getOrCreateByName(String name) {
        return commodityMapper.map(commodityRepository.findOrCreateByName(name));
    }
}
