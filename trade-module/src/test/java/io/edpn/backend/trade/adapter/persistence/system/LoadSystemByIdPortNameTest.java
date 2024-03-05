package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemByIdPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadSystemByIdPortNameTest {

    @Mock
    private SystemEntityMapper systemEntityMapper;

    @Mock
    private FindSystemFilterMapper persistenceFindSystemFilterMapper;

    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    private LoadSystemByIdPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemRepository(systemEntityMapper, persistenceFindSystemFilterMapper, mybatisSystemRepository);
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        SystemEntity mockSystemEntity = mock(SystemEntity.class);
        System mockSystem = mock(System.class);

        when(mybatisSystemRepository.findById(id)).thenReturn(Optional.of(mockSystemEntity));
        when(systemEntityMapper.map(mockSystemEntity)).thenReturn(mockSystem);

        Optional<System> results = underTest.loadById(id);

        verify(mybatisSystemRepository).findById(id);
        verify(systemEntityMapper).map(mockSystemEntity);
        verifyNoMoreInteractions(mybatisSystemRepository, systemEntityMapper);

        assertThat(results.isPresent(), is(true));
        assertThat(results.get(), equalTo(mockSystem));
    }

    @Test
    void findByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(mybatisSystemRepository.findById(id)).thenReturn(Optional.empty());
        Optional<System> result = underTest.loadById(id);

        verify(mybatisSystemRepository).findById(id);
        verify(systemEntityMapper, never()).map(any(SystemEntity.class));
        verifyNoMoreInteractions(mybatisSystemRepository, systemEntityMapper);

        assertThat(result, equalTo(Optional.empty()));
    }
}
