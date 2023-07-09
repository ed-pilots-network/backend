package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.CommodityMarketInfoMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityMarketInfoEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MybatisCommodityMarketInfoRepositoryTest {

    @Mock
    private CommodityMarketInfoMapper commodityMarketInfoMapper;
    @Mock
    private CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper;

    private CommodityMarketInfoRepository underTest;

    @BeforeEach
    public void setup() {
        underTest = new MybatisCommodityMarketInfoRepository(commodityMarketInfoMapper, commodityMarketInfoEntityMapper);
    }

    @Test
    public void testFindCommodityMarketInfoByCommodityId() {
        // mock objects
        UUID commodityId = UUID.randomUUID();
        CommodityMarketInfoEntity marketInfoEntity = mock(CommodityMarketInfoEntity.class);
        CommodityMarketInfo marketInfo = mock(CommodityMarketInfo.class);

        when(commodityMarketInfoEntityMapper.findByCommodityId(commodityId)).thenReturn(Optional.of(marketInfoEntity));
        when(commodityMarketInfoMapper.map(marketInfoEntity)).thenReturn(marketInfo);

        Optional<CommodityMarketInfo> result = underTest.findCommodityMarketInfoByCommodityId(commodityId);

        verify(commodityMarketInfoEntityMapper).findByCommodityId(commodityId);
        verify(commodityMarketInfoMapper).map(marketInfoEntity);

        assertThat(result, equalTo(Optional.of(marketInfo)));
    }

    @Test
    public void testFindCommodityMarketInfoByCommodityIdNotFound() {
        // mock objects
        UUID commodityId = UUID.randomUUID();
        CommodityMarketInfoEntity marketInfoEntity = mock(CommodityMarketInfoEntity.class);
        CommodityMarketInfo marketInfo = mock(CommodityMarketInfo.class);

        when(commodityMarketInfoEntityMapper.findByCommodityId(commodityId)).thenReturn(Optional.empty());

        Optional<CommodityMarketInfo> result = underTest.findCommodityMarketInfoByCommodityId(commodityId);

        verify(commodityMarketInfoEntityMapper).findByCommodityId(commodityId);
        verify(commodityMarketInfoMapper, never()).map(marketInfoEntity);

        assertThat(result, equalTo(Optional.empty()));
    }

    @Test
    public void testFindAllCommodityMarketInfo() {
        // mock objects
        CommodityMarketInfoEntity marketInfoEntity = mock(CommodityMarketInfoEntity.class);
        CommodityMarketInfo marketInfo = mock(CommodityMarketInfo.class);

        when(commodityMarketInfoEntityMapper.findAll()).thenReturn(Collections.singletonList(marketInfoEntity));
        when(commodityMarketInfoMapper.map(marketInfoEntity)).thenReturn(marketInfo);

        List<CommodityMarketInfo> result = underTest.findAllCommodityMarketInfo();

        verify(commodityMarketInfoEntityMapper).findAll();
        verify(commodityMarketInfoMapper).map(marketInfoEntity);

        assertThat(result, equalTo(Collections.singletonList(marketInfo)));
    }
}
