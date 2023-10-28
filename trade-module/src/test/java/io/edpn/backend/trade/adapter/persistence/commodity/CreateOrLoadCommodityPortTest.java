package io.edpn.backend.trade.adapter.persistence.commodity;

import io.edpn.backend.trade.adapter.persistence.CommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.commodity.CreateOrLoadCommodityPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateOrLoadCommodityPortTest {

    @Mock
    private CommodityEntityMapper<MybatisCommodityEntity> mybatisCommodityEntityMapper;

    @Mock
    private MybatisCommodityRepository mybatisCommodityRepository;

    private CreateOrLoadCommodityPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new CommodityRepository(mybatisCommodityEntityMapper, mybatisCommodityRepository);
    }

    @Test
    void findOrCreateByNameNew() {
        Commodity inputCommodity = mock(Commodity.class);
        MybatisCommodityEntity inputCommodityEntity = mock(MybatisCommodityEntity.class);
        when(mybatisCommodityEntityMapper.map(inputCommodity)).thenReturn(inputCommodityEntity);


        MybatisCommodityEntity outputCommodityEntity = mock(MybatisCommodityEntity.class);
        Commodity expectedCommodity = mock(Commodity.class);
        when(mybatisCommodityEntityMapper.map(outputCommodityEntity)).thenReturn(expectedCommodity);

        when(mybatisCommodityRepository.createOrUpdateOnConflict(inputCommodityEntity)).thenReturn(outputCommodityEntity);

        Commodity result = underTest.createOrLoad(inputCommodity);
        assertThat(result, is(expectedCommodity));
    }
}
