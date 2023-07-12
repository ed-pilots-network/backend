package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.LocateCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.LocateCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.LocateCommodityEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(MockitoExtension.class)
class MybatisLocateCommodityRepositoryTest {

    @Mock
    private LocateCommodityMapper locateCommodityMapper;
    @Mock
    private LocateCommodityEntityMapper locateCommodityEntityMapper;
    @Mock
    private LocateCommodityFilterMapper locateCommodityFilterMapper;

    private LocateCommodityRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new MybatisLocateCommodityRepository(locateCommodityMapper, locateCommodityEntityMapper, locateCommodityFilterMapper);
    }

    @Test
    void locateCommodityByFilter() {
        fail("add tests");
    }
}