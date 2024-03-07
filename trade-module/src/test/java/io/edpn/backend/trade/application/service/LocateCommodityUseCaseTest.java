package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocateCommodityUseCaseTest {

    @Mock
    private LocateCommodityByFilterPort locateCommodityByFilterPort;

    private LocateCommodityUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityService(locateCommodityByFilterPort);
    }

    @Test
    void shouldLocateCommoditiesNearby() {
        LocateCommodityFilter locateCommodityFilter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);

        when(locateCommodityByFilterPort.locateCommodityByFilter(locateCommodityFilter)).thenReturn(List.of(locateCommodity));

        List<LocateCommodity> responses = underTest.locateCommodityOrderByDistance(locateCommodityFilter);

        assertThat(responses, contains(locateCommodity));
    }

}
