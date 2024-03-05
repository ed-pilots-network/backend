package io.edpn.backend.trade.adapter.persistence.marketdatum;

import io.edpn.backend.trade.adapter.persistence.MarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisMarketDatumEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisMarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateWhenNotExistsMarketDatumPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateWhenNotExistsMarketDatumPortTest {

    @Mock
    private MybatisMarketDatumRepository mybatisMarketDatumRepository;

    @Mock
    private MybatisMarketDatumEntityMapper mybatisMarketDatumEntityMapper;

    private CreateWhenNotExistsMarketDatumPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MarketDatumRepository(mybatisMarketDatumRepository, mybatisMarketDatumEntityMapper);
    }

    @Test
    void testCreateWhenNotExists() {
        UUID uuid = UUID.randomUUID();
        MarketDatum inputMarketDatum = mock(MarketDatum.class);
        MybatisMarketDatumEntity inputMybatisMarketDatumEntity = mock(MybatisMarketDatumEntity.class);

        when(mybatisMarketDatumEntityMapper.map(inputMarketDatum)).thenReturn(inputMybatisMarketDatumEntity);

        underTest.createWhenNotExists(uuid, inputMarketDatum);

        verify(mybatisMarketDatumEntityMapper, times(1)).map(inputMarketDatum);
        verify(mybatisMarketDatumRepository, times(1)).insertWhenNotExists(uuid, inputMybatisMarketDatumEntity);
    }
}
