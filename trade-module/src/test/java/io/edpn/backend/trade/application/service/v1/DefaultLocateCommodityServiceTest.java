package io.edpn.backend.trade.application.service.v1;

import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.application.mappers.v1.LocateCommodityDTOMapper;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.service.v1.LocateCommodityService;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultLocateCommodityServiceTest {

    @Mock
    private LocateCommodityUseCase locateCommodityUseCase;
    @Mock
    private LocateCommodityDTOMapper locateCommodityDTOMapper;

    private LocateCommodityService underTest;

    @BeforeEach
    void setUp() {
        underTest = new DefaultLocateCommodityService(locateCommodityUseCase, locateCommodityDTOMapper);
    }

    @Test
    void shouldLocateCommoditiesNearby() {
        LocateCommodityRequest locateCommodityRequest = mock(LocateCommodityRequest.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);
        LocateCommodityResponse locateCommodityResponse = LocateCommodityResponse.builder().build();
        LocateCommodityFilter locateCommodityFilter = new LocateCommodityFilter();

        when(locateCommodityDTOMapper.map(locateCommodityRequest)).thenReturn(locateCommodityFilter);
        when(locateCommodityUseCase.locateCommoditiesOrderByDistance(locateCommodityFilter)).thenReturn(List.of(locateCommodity));
        when(locateCommodityDTOMapper.map(locateCommodity)).thenReturn(locateCommodityResponse);

        List<LocateCommodityResponse> responses = underTest.locateCommoditiesOrderByDistance(locateCommodityRequest);

        ArgumentCaptor<LocateCommodityFilter> argumentCaptor = ArgumentCaptor.forClass(LocateCommodityFilter.class);
        verify(locateCommodityUseCase).locateCommoditiesOrderByDistance(argumentCaptor.capture());

        LocateCommodityFilter capturedLocateCommodity = argumentCaptor.getValue();

        assertThat(capturedLocateCommodity, equalTo(locateCommodityFilter));
        assertThat(responses, equalTo(List.of(locateCommodityResponse)));
    }
}
