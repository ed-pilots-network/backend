package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.commodity.LoadOrCreateCommodityByNamePort;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.ExistsByStationNameAndSystemNameAndTimestampPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiveCommodityMessageUseCaseTest {
    
    @Mock
    private ExistsByStationNameAndSystemNameAndTimestampPort existsByStationNameAndSystemNameAndTimestamp;
    
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    
    @Mock
    private LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
    
    @Mock
    private LoadOrCreateCommodityByNamePort loadOrCreateCommodityByNamePort;
    
    @Mock
    private UpdateStationPort updateStationPort;
    
    @Mock
    private List<RequestDataUseCase<Station>> stationRequestDataServices;
    
    @Mock
    private List<RequestDataUseCase<System>> systemRequestDataServices;
    
    private ReceiveKafkaMessageUseCase<CommodityMessage.V3> underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new ReceiveCommodityMessageService(
                existsByStationNameAndSystemNameAndTimestamp,
                loadOrCreateSystemByNamePort,
                loadOrCreateBySystemAndStationNamePort,
                loadOrCreateCommodityByNamePort,
                updateStationPort,
                stationRequestDataServices,
                systemRequestDataServices);
    }
    
    @Test
    public void shouldReceiveMessage(){
        
        CommodityMessage.V3 message = mock(CommodityMessage.V3.class);
        
        LocalDateTime timestamp = LocalDateTime.now();
        
        CommodityMessage.V3.Commodity mockCommodity = mock(CommodityMessage.V3.Commodity.class);
        
        CommodityMessage.V3.Commodity[] list = new CommodityMessage.V3.Commodity[1];
        list[0] = mockCommodity;
        
        CommodityMessage.V3.Payload payload =
                new CommodityMessage.V3.Payload(
                        "system",
                        "station",
                        123456L,
                        true,
                        true,
                        timestamp.toString(),
                        null,
                        null,
                        list);
        
        when(message.message()).thenReturn(payload);
        
        System system = mock(System.class);
        when(loadOrCreateSystemByNamePort.loadOrCreateSystemByName("system")).thenReturn(system);
        
        Station station = mock(Station.class);
        when(loadOrCreateBySystemAndStationNamePort.loadOrCreateBySystemAndStationName(system, "station")).thenReturn(station);
        
        underTest.receive(message);
        
        verify(loadOrCreateSystemByNamePort, times(1)).loadOrCreateSystemByName(anyString());
        verify(loadOrCreateBySystemAndStationNamePort, times(1)).loadOrCreateBySystemAndStationName(any(), anyString());
        verify(loadOrCreateSystemByNamePort, times(1)).loadOrCreateSystemByName(any());
        verify(updateStationPort, times(1)).update(any());
        
    }
}