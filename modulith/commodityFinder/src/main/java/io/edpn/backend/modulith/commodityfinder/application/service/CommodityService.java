package io.edpn.backend.modulith.commodityfinder.application.service;

import io.edpn.backend.modulith.commodityfinder.domain.entity.Commodity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommodityService {

    public Commodity getOrCreateByName(String name) {
        return Commodity.builder().build(); //TODO
    }
}
