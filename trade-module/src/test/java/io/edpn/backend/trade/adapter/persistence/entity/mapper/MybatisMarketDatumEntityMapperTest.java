package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisMarketDatumEntity;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.dto.persistence.entity.MarketDatumEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.MarketDatumEntityMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MybatisMarketDatumEntityMapperTest {
    
    @Mock
    private CommodityEntityMapper<MybatisCommodityEntity> mybatisCommodityEntityMapper;
    
    private MarketDatumEntityMapper<MybatisMarketDatumEntity> underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new MybatisMarketDatumEntityMapper(mybatisCommodityEntityMapper);
    }
    
    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        // create mock objects
        Commodity mockCommodity = mock(Commodity.class);
        
        LocalDateTime timestamp = LocalDateTime.now();
        List<String> statusFlags = Arrays.asList("status1", "status2");
        
        // Setup the MarketDatum Entity with test data
        MarketDatumEntity entity = MybatisMarketDatumEntity.builder()
                .commodity(mock(MybatisCommodityEntity.class))
                .timestamp(timestamp)
                .meanPrice(100L)
                .buyPrice(50L)
                .stock(200L)
                .stockBracket(10L)
                .sellPrice(150L)
                .demand(300L)
                .demandBracket(20L)
                .statusFlags(statusFlags)
                .prohibited(false)
                .build();
        
        when(mybatisCommodityEntityMapper.map(entity.getCommodity())).thenReturn(mockCommodity);
        
        // Map the entity to a MarketDatum object
        MarketDatum domainObject = underTest.map(entity);
        
        // Verify that the result matches the expected values
        assertThat(domainObject.commodity(), is(mockCommodity));
        assertThat(domainObject.timestamp(), is(timestamp));
        assertThat(domainObject.meanPrice(), is(100L));
        assertThat(domainObject.buyPrice(), is(50L));
        assertThat(domainObject.stock(), is(200L));
        assertThat(domainObject.stockBracket(), is(10L));
        assertThat(domainObject.sellPrice(), is(150L));
        assertThat(domainObject.demand(), is(300L));
        assertThat(domainObject.demandBracket(), is(20L));
        assertThat(domainObject.statusFlags(), is(statusFlags));
        assertThat(domainObject.prohibited(), is(false));
        
        verify(mybatisCommodityEntityMapper, times(1)).map(entity.getCommodity());
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        // create mock objects
        MybatisCommodityEntity mockCommodityEntity = mock(MybatisCommodityEntity.class);
        
        LocalDateTime timestamp = LocalDateTime.now();
        List<String> statusFlags = Arrays.asList("status1", "status2");
        
        // Setup the MarketDatum object with test data
        MarketDatum domainObject = new MarketDatum(
                mock(Commodity.class),
                timestamp,
                100L,
                50L,
                200L,
                10L,
                150L,
                300L,
                20L,
                statusFlags,
                false
        );

        when(mybatisCommodityEntityMapper.map(domainObject.commodity())).thenReturn(mockCommodityEntity);
        
        // Map the domainObject to a MarketDatum entity
        MarketDatumEntity entity = underTest.map(domainObject);
        
        // Verify that the result matches the expected values
        assertThat(entity.getCommodity(), is(mockCommodityEntity));
        assertThat(entity.getTimestamp(), is(timestamp));
        assertThat(entity.getMeanPrice(), is(100L));
        assertThat(entity.getBuyPrice(), is(50L));
        assertThat(entity.getStock(), is(200L));
        assertThat(entity.getStockBracket(), is(10L));
        assertThat(entity.getSellPrice(), is(150L));
        assertThat(entity.getDemand(), is(300L));
        assertThat(entity.getDemandBracket(), is(20L));
        assertThat(entity.getStatusFlags(), is(statusFlags));
        assertThat(entity.isProhibited(), is(false));

        verify(mybatisCommodityEntityMapper, times(1)).map(domainObject.commodity());
    }
}