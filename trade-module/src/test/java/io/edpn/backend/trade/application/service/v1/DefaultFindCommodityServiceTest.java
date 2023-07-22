package io.edpn.backend.trade.application.service.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.application.mappers.v1.FindCommodityDTOMapper;
import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.domain.service.v1.FindCommodityService;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DefaultFindCommodityServiceTest {
    
    @Mock
    private FindCommodityUseCase findCommodityUseCase;
    @Mock
    private FindCommodityDTOMapper findCommodityDTOMapper;
    
    private FindCommodityService underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new DefaultFindCommodityService(findCommodityUseCase, findCommodityDTOMapper);
    }
    
    @Test
    void shouldFindValidatedCommodityById() {
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        FindCommodityResponse findCommodityResponse = mock(FindCommodityResponse.class);
        UUID id = UUID.randomUUID();
        
        when(findCommodityUseCase.findById(id)).thenReturn(Optional.of(validatedCommodity));
        when(findCommodityDTOMapper.map(validatedCommodity)).thenReturn(findCommodityResponse);
        
        Optional<FindCommodityResponse> response = underTest.findById(id);
        
        assertThat(response, equalTo(Optional.of(findCommodityResponse)));
    }
    
    @Test
    void shouldFindAllValidatedCommodities(){
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        FindCommodityResponse findCommodityResponse = mock(FindCommodityResponse.class);
        List<FindCommodityResponse> findCommodityResponseList = Collections.singletonList(findCommodityResponse);
        
        
        when(findCommodityUseCase.findAll()).thenReturn(List.of(validatedCommodity));
        when(findCommodityDTOMapper.map(validatedCommodity)).thenReturn(findCommodityResponse);
        
        List<FindCommodityResponse> responseList = underTest.findAll();
        
        assertThat(responseList, equalTo(findCommodityResponseList));
    }
    
    @Test
    void shouldFindValidatedCommodityByFilter(){
        FindCommodityRequest findCommodityRequest = mock(FindCommodityRequest.class);
        FindCommodityFilter findCommodityFilter = mock(FindCommodityFilter.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        FindCommodityResponse findCommodityResponse = mock(FindCommodityResponse.class);
        List<FindCommodityResponse> findCommodityResponseList = Collections.singletonList(findCommodityResponse);
        
        when(findCommodityDTOMapper.map(findCommodityRequest)).thenReturn(findCommodityFilter);
        when(findCommodityUseCase.findByFilter(findCommodityFilter)).thenReturn(List.of(validatedCommodity));
        when(findCommodityDTOMapper.map(validatedCommodity)).thenReturn(findCommodityResponse);
        
        List<FindCommodityResponse> responseList = underTest.findByFilter(findCommodityRequest);
        
        verify(findCommodityUseCase).findByFilter(findCommodityFilter);
        
        assertThat(responseList, equalTo(findCommodityResponseList));
    }
    

}