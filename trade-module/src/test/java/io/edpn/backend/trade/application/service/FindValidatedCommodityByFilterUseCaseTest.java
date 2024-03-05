package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterUseCase;
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
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindValidatedCommodityByFilterUseCaseTest {

    @Mock
    private LoadAllValidatedCommodityPort loadAllValidatedCommodityPort;

    @Mock
    private LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort;

    @Mock
    private LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort;

    private FindValidatedCommodityByFilterUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort);
    }

    @Test
    public void testValidatedCommoditiesFoundByFilter() {
        FindCommodityFilter mockFindCommodityFilter = mock(FindCommodityFilter.class);
        ValidatedCommodity mockValidatedCommodity = mock(ValidatedCommodity.class);

        when(loadValidatedCommodityByFilterPort.loadByFilter(mockFindCommodityFilter)).thenReturn(List.of(mockValidatedCommodity));

        List<ValidatedCommodity> responseList = underTest.findByFilter(mockFindCommodityFilter);

        assertThat(responseList, equalTo(mockValidatedCommodity));
    }
}
