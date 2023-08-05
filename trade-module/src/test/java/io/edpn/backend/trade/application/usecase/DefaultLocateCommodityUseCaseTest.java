package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultLocateCommodityUseCaseTest {

    @Mock
    private LocateCommodityRepository locateCommodityRepository;

    private LocateCommodityUseCase underTest;

    @BeforeEach
    public void setup() {
        underTest = new DefaultLocateCommodityUseCase(locateCommodityRepository);
    }

    @Test
    public void testFindAll() {
        LocateCommodityFilter mockLocateCommodityFilter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);
        List<LocateCommodity> expectedLocateCommodityList = Collections.singletonList(locateCommodity);

        when(locateCommodityRepository.locateCommodityByFilter(mockLocateCommodityFilter)).thenReturn(expectedLocateCommodityList);

        List<LocateCommodity> actualMarketInfoList = underTest.locateCommoditiesOrderByDistance(mockLocateCommodityFilter);

        verify(locateCommodityRepository).locateCommodityByFilter(mockLocateCommodityFilter);
        verifyNoMoreInteractions(locateCommodityRepository);

        assertThat(actualMarketInfoList, equalTo(expectedLocateCommodityList));
    }
}
