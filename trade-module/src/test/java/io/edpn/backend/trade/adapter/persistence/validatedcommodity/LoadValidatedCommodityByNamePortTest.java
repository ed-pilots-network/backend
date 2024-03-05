package io.edpn.backend.trade.adapter.persistence.validatedcommodity;

import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindCommodityFilterMapper;
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
    private ValidatedCommodityEntityMapper validatedCommodityEntityMapper;

    @Mock
    private FindCommodityFilterMapper findCommodityFilterMapper;

    private LoadValidatedCommodityByNamePort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, validatedCommodityEntityMapper, findCommodityFilterMapper);
    }

    @Test
    void findCommodityByName() {
        ValidatedCommodityEntity validatedCommodityEntity = mock(ValidatedCommodityEntity.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        String displayName = "DisplayName";


        when(mybatisValidatedCommodityRepository.findByName(displayName)).thenReturn(Optional.of(validatedCommodityEntity));
        when(validatedCommodityEntityMapper.map(validatedCommodityEntity)).thenReturn(validatedCommodity);

        Optional<ValidatedCommodity> result = underTest.loadByName(displayName);

        verify(mybatisValidatedCommodityRepository).findByName(displayName);
        verify(validatedCommodityEntityMapper).map(validatedCommodityEntity);
        verifyNoMoreInteractions(mybatisValidatedCommodityRepository, validatedCommodityEntity, findCommodityFilterMapper);

        assertThat(result, equalTo(Optional.of(validatedCommodity)));
    }
}
