package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.domain.repository.ValidatedCommodityRepository;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultFindCommodityUseCaseTest {
    
    @Mock
    private ValidatedCommodityRepository validatedCommodityRepository;
    
    private FindCommodityUseCase underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new DefaultFindCommodityUseCase(validatedCommodityRepository);
    }
    
    @Test
    public void shouldFindByName(){
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        String displayName = "Commodity Name";
        when(validatedCommodityRepository.findByName(displayName)).thenReturn(Optional.ofNullable(validatedCommodity));
        
        Optional<ValidatedCommodity> actualValidatedCommodity = underTest.findByName(displayName);
        
        verify(validatedCommodityRepository).findByName(displayName);
        verifyNoMoreInteractions(validatedCommodity);
        assert validatedCommodity != null;
        assertThat(Optional.of(validatedCommodity), equalTo(actualValidatedCommodity));
    }
    
    @Test
    public void shouldFindAll(){
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        List<ValidatedCommodity> validatedCommodityList = List.of(validatedCommodity);
        
        when(validatedCommodityRepository.findAll()).thenReturn(validatedCommodityList);
        
        List<ValidatedCommodity> actualValidatedCommoditiesList = underTest.findAll();
        
        verify(validatedCommodityRepository).findAll();
        verifyNoMoreInteractions(validatedCommodityRepository);
        
        assertThat(validatedCommodityList, equalTo(actualValidatedCommoditiesList));
    }
    
    @Test
    public void shouldFindByFilter(){
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        List<ValidatedCommodity> validatedCommodityList = List.of(validatedCommodity);
        FindCommodityFilter findCommodityFilter = mock(FindCommodityFilter.class);
        
        when(validatedCommodityRepository.findByFilter(findCommodityFilter)).thenReturn(validatedCommodityList);
        
        List<ValidatedCommodity> actualValidatedCommoditiesList = underTest.findByFilter(findCommodityFilter);
        
        verify(validatedCommodityRepository).findByFilter(findCommodityFilter);
        verifyNoMoreInteractions(validatedCommodityRepository);
        
        assertThat(validatedCommodityList, equalTo(actualValidatedCommoditiesList));
    }
    
    
    
}