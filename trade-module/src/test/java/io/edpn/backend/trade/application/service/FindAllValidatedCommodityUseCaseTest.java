package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllValidatedCommodityUseCaseTest {

    @Mock
    private LoadAllValidatedCommodityPort loadAllValidatedCommodityPort;

    @Mock
    private LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort;

    @Mock
    private LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort;

    private FindAllValidatedCommodityUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort);
    }

    @Test
    public void testFindAllValidateCommodities() {
        ValidatedCommodity validatedCommodity1 = mock(ValidatedCommodity.class);
        ValidatedCommodity validatedCommodity2 = mock(ValidatedCommodity.class);
        ValidatedCommodity validatedCommodity3 = mock(ValidatedCommodity.class);

        when(loadAllValidatedCommodityPort.loadAll()).thenReturn(List.of(validatedCommodity1, validatedCommodity2, validatedCommodity3));

        List<ValidatedCommodity> result = underTest.findAll();

        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(validatedCommodity1, validatedCommodity2, validatedCommodity3));

    }

}