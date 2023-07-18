package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.repository.CommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.CommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MybatisCommodityRepositoryTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private CommodityMapper commodityMapper;
    @Mock
    private CommodityEntityMapper commodityEntityMapper;

    private CommodityRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new MybatisCommodityRepository(idGenerator, commodityMapper, commodityEntityMapper);
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        CommodityEntity mockCommodityEntity = mock(CommodityEntity.class);
        Commodity mockCommodity = mock(Commodity.class);

        when(commodityEntityMapper.findById(id)).thenReturn(Optional.of(mockCommodityEntity));
        when(commodityMapper.map(mockCommodityEntity)).thenReturn(mockCommodity);

        Optional<Commodity> results = underTest.findById(id);

        verify(commodityEntityMapper).findById(id);
        verify(commodityMapper).map(mockCommodityEntity);
        verifyNoMoreInteractions(commodityEntityMapper, commodityMapper, idGenerator);

        assertThat(results.isPresent(), is(true));
        assertThat(results.get(), equalTo(mockCommodity));
    }

    @Test
    void findByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(commodityEntityMapper.findById(id)).thenReturn(Optional.empty());
        Optional<Commodity> result = underTest.findById(id);

        verify(commodityEntityMapper).findById(id);
        verify(commodityMapper, never()).map(any(CommodityEntity.class));
        verifyNoMoreInteractions(commodityEntityMapper, commodityMapper, idGenerator);

        assertThat(result, equalTo(Optional.empty()));
    }

    @Test
    void createWithoutId() {
        UUID id = UUID.randomUUID();
        Commodity inputCommodity = mock(Commodity.class);
        CommodityEntity mockCommodityEntityWithoutId = mock(CommodityEntity.class);
        CommodityEntity mockSavedCommodityEntity = mock(CommodityEntity.class);

        Commodity expected = mock(Commodity.class);

        when(commodityMapper.map(inputCommodity)).thenReturn(mockCommodityEntityWithoutId);
        when(mockCommodityEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(commodityEntityMapper.findById(id)).thenReturn(Optional.ofNullable(mockSavedCommodityEntity));
        when(commodityMapper.map(mockSavedCommodityEntity)).thenReturn(expected);

        Commodity result = underTest.create(inputCommodity);

        verify(commodityMapper).map(inputCommodity);
        verify(idGenerator).generateId();
        verify(commodityEntityMapper).insert(any());
        verify(commodityEntityMapper).findById(id);
        verify(commodityMapper).map(mockSavedCommodityEntity);
        verifyNoMoreInteractions(commodityEntityMapper, commodityMapper, idGenerator);

        assertThat(result, is(expected));
    }

    @Test
    void createWithId() {
        UUID id = UUID.randomUUID();
        Commodity inputCommodity = mock(Commodity.class);
        CommodityEntity mockCommodityEntity = mock(CommodityEntity.class);

        Commodity expected = mock(Commodity.class);

        when(commodityMapper.map(inputCommodity)).thenReturn(mockCommodityEntity);
        when(mockCommodityEntity.getId()).thenReturn(id);
        when(commodityEntityMapper.findById(id)).thenReturn(Optional.ofNullable(mockCommodityEntity));
        when(commodityMapper.map(mockCommodityEntity)).thenReturn(expected);

        Commodity result = underTest.create(inputCommodity);

        verify(commodityMapper).map(inputCommodity);
        verify(idGenerator, never()).generateId();
        verify(commodityEntityMapper).insert(any());
        verify(commodityEntityMapper).findById(id);
        verify(commodityMapper).map(mockCommodityEntity);
        verifyNoMoreInteractions(commodityEntityMapper, commodityMapper, idGenerator);

        assertThat(result, is(expected));
    }

    @Test
    void createWithError() {
        UUID id = UUID.randomUUID();
        Commodity inputCommodity = mock(Commodity.class);
        CommodityEntity mockCommodityEntity = mock(CommodityEntity.class);

        when(inputCommodity.getId()).thenReturn(id);
        when(commodityMapper.map(inputCommodity)).thenReturn(mockCommodityEntity);
        when(mockCommodityEntity.getId()).thenReturn(id);
        when(commodityEntityMapper.findById(id)).thenReturn(Optional.empty());

        Exception result = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.create(inputCommodity));

        verify(commodityMapper).map(inputCommodity);
        verify(idGenerator, never()).generateId();
        verify(commodityEntityMapper).insert(any());
        verify(commodityEntityMapper).findById(id);
        verifyNoMoreInteractions(commodityEntityMapper, commodityMapper, idGenerator);

        assertThat(result.getMessage(), is("commodity with id: " + id + " could not be found after create"));
    }

    @Test
    void findOrCreateByNameNew() {
        String name = "Test Commodity";
        UUID id = UUID.randomUUID();
        CommodityEntity mockCommodityEntityWithoutId = mock(CommodityEntity.class);
        CommodityEntity mockSavedCommodityEntity = mock(CommodityEntity.class);

        Commodity expected = mock(Commodity.class);

        when(commodityEntityMapper.findByName(name)).thenReturn(Optional.empty());
        when(commodityMapper.map(argThat((Commodity input) -> input.getId() == null && input.getName().equals(name)))).thenReturn(mockCommodityEntityWithoutId);
        when(mockCommodityEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(commodityEntityMapper.findById(id)).thenReturn(Optional.ofNullable(mockSavedCommodityEntity));
        when(commodityMapper.map(mockSavedCommodityEntity)).thenReturn(expected);

        Commodity result = underTest.findOrCreateByName(name);

        verify(commodityEntityMapper).findByName(name);
        verify(commodityMapper).map(argThat((Commodity input) -> input.getId() == null && input.getName().equals(name)));
        verify(idGenerator).generateId();
        verify(commodityEntityMapper).insert(any());
        verify(commodityEntityMapper).findById(id);
        verify(commodityMapper).map(mockSavedCommodityEntity);
        verifyNoMoreInteractions(commodityEntityMapper, commodityMapper, idGenerator);

        assertThat(result, is(expected));
    }

    @Test
    void findOrCreateByNameFound() {
        String name = "Test Commodity";
        CommodityEntity mockSavedCommodityEntity = mock(CommodityEntity.class);

        Commodity expected = mock(Commodity.class);

        when(commodityEntityMapper.findByName(name)).thenReturn(Optional.of(mockSavedCommodityEntity));
        when(commodityMapper.map(mockSavedCommodityEntity)).thenReturn(expected);

        Commodity result = underTest.findOrCreateByName(name);

        verify(commodityEntityMapper).findByName(name);
        verify(commodityMapper).map(mockSavedCommodityEntity);
        verifyNoMoreInteractions(commodityEntityMapper, commodityMapper, idGenerator);

        assertThat(result, is(expected));
    }
}