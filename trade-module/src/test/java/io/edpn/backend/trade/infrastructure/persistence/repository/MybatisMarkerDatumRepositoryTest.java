package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.repository.MarketDatumRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MybatisMarkerDatumRepositoryTest {

    @Mock
    private MarketDatumEntityMapper marketDatumEntityMapper;

    private MarketDatumRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new MybatisMarkerDatumRepository(marketDatumEntityMapper);
    }

    @Test
    void existsByStationNameAndSystemNameAndTimestamp() {
        String systemName = "Test System";
        String stationName = "Test Station";
        LocalDateTime timestamp = LocalDateTime.now();

        when(marketDatumEntityMapper.existsByStationNameAndSystemNameAndTimestamp(any(), any(), any())).thenReturn(true);

        boolean result = underTest.existsByStationNameAndSystemNameAndTimestamp(systemName, stationName, timestamp);

        verify(marketDatumEntityMapper).existsByStationNameAndSystemNameAndTimestamp(any(), any(), any());
        verifyNoMoreInteractions(marketDatumEntityMapper);

        assertThat(result, equalTo(true));
    }
}