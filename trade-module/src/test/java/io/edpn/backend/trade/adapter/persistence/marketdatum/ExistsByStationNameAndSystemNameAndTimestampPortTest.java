package io.edpn.backend.trade.adapter.persistence.marketdatum;

import io.edpn.backend.trade.adapter.persistence.MarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.ExistsByStationNameAndSystemNameAndTimestampPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistsByStationNameAndSystemNameAndTimestampPortTest {
    
    @Mock
    private MybatisMarketDatumRepository mybatisMarketDatumRepository;

    private ExistsByStationNameAndSystemNameAndTimestampPort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new MarketDatumRepository(mybatisMarketDatumRepository);
    }
    
    @Test
    public void testExists(){
        String systemName = "system";
        String stationName = "station";
        LocalDateTime timestamp  = LocalDateTime.now();
        
        when(mybatisMarketDatumRepository.existsByStationNameAndSystemNameAndTimestamp(systemName, stationName, timestamp)).thenReturn(true);
        
        boolean result = underTest.exists(systemName, stationName, timestamp);
        
        verify(mybatisMarketDatumRepository, times(1)).existsByStationNameAndSystemNameAndTimestamp(systemName,stationName, timestamp);
        
        assertThat(result, equalTo(true));
    }
}
