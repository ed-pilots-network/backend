package io.edpn.backend.trade.adapter.persistence.validatedcommodity;

import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadValidatedCommodityByNamePortTest {

    @Mock
    private MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository;

    @Mock
    private MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper;

    @Mock
    private MybatisFindCommodityFilterMapper mybatisFindCommodityFilterMapper;

    private LoadValidatedCommodityByNamePort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntityMapper, mybatisFindCommodityFilterMapper);
    }

    @Test
    void findCommodityByName() {
        MybatisValidatedCommodityEntity mybatisValidatedCommodityEntity = mock(MybatisValidatedCommodityEntity.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        String displayName = "DisplayName";


        when(mybatisValidatedCommodityRepository.findByName(displayName)).thenReturn(Optional.of(mybatisValidatedCommodityEntity));
        when(mybatisValidatedCommodityEntityMapper.map(mybatisValidatedCommodityEntity)).thenReturn(validatedCommodity);

        Optional<ValidatedCommodity> result = underTest.loadByName(displayName);

        verify(mybatisValidatedCommodityRepository).findByName(displayName);
        verify(mybatisValidatedCommodityEntityMapper).map(mybatisValidatedCommodityEntity);
        verifyNoMoreInteractions(mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntity, mybatisFindCommodityFilterMapper);

        assertThat(result, equalTo(Optional.of(validatedCommodity)));
    }
}
