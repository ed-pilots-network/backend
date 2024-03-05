package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.adapter.web.dto.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNameUseCase;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindValidatedCommodityByNameUseCaseTest {

    @Mock
    private LoadAllValidatedCommodityPort loadAllValidatedCommodityPort;

    @Mock
    private LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort;

    @Mock
    private LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort;

    private FindValidatedCommodityByNameUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort);
    }

    @Test
    public void testValidatedCommodityFoundByDisplayName() {
        ValidatedCommodity mockValidatedCommodity = mock(ValidatedCommodity.class);
        ValidatedCommodityDto mockValidatedCommodityDto = mock(ValidatedCommodityDto.class);
        String displayName = "Commodity Name";

        when(loadValidatedCommodityByNamePort.loadByName(displayName)).thenReturn(Optional.ofNullable(mockValidatedCommodity));

        Optional<ValidatedCommodity> response = underTest.findByName(displayName);

        assertThat(response, equalTo(Optional.ofNullable(mockValidatedCommodityDto)));
    }
}
