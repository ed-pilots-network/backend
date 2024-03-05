package io.edpn.backend.trade.adapter.persistence.commodity;

import io.edpn.backend.trade.adapter.persistence.CommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.application.domain.Commodity;
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
    private MybatisCommodityEntityMapper mybatisCommodityEntityMapper;

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
        MybatisCommodityEntity inputMybatisCommodityEntity = mock(MybatisCommodityEntity.class);
        when(mybatisCommodityEntityMapper.map(inputCommodity)).thenReturn(inputMybatisCommodityEntity);


        MybatisCommodityEntity outputMybatisCommodityEntity = mock(MybatisCommodityEntity.class);
        Commodity expectedCommodity = mock(Commodity.class);
        when(mybatisCommodityEntityMapper.map(outputMybatisCommodityEntity)).thenReturn(expectedCommodity);

        when(mybatisCommodityRepository.createOrUpdateOnConflict(inputMybatisCommodityEntity)).thenReturn(outputMybatisCommodityEntity);

        Commodity result = underTest.createOrLoad(inputCommodity);
        assertThat(result, is(expectedCommodity));
    }
}
