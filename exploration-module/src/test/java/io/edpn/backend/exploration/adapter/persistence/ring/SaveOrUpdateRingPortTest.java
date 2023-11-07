package io.edpn.backend.exploration.adapter.persistence.ring;

import io.edpn.backend.exploration.adapter.persistence.MybatisRingRepository;
import io.edpn.backend.exploration.adapter.persistence.RingRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.ring.SaveOrUpdateRingPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveOrUpdateRingPortTest {
    @Mock
    private MybatisRingRepository mybatisRingRepository;
    
    @Mock
    private RingEntityMapper<MybatisRingEntity> ringEntityMapper;
    
    private SaveOrUpdateRingPort underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new RingRepository(mybatisRingRepository, ringEntityMapper);
    }
    
    @Test
    void save_shouldUpdateAndLoad() {
        
        Ring ring = mock(Ring.class);
        MybatisRingEntity entity = mock(MybatisRingEntity.class);
        Ring loaded = mock(Ring.class);
        when(ringEntityMapper.map(ring)).thenReturn(entity);
        when(mybatisRingRepository.insertOrUpdateOnConflict(any())).thenReturn(entity);
        when(ringEntityMapper.map(entity)).thenReturn(loaded);
        
        
        Ring result = underTest.saveOrUpdate(ring);
        
        
        assertThat(result, is(loaded));
    }
}
