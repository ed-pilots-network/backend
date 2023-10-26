package io.edpn.backend.exploration.adapter.persistence.body;

import io.edpn.backend.exploration.adapter.persistence.BodyRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisBodyRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.BodyEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.body.SaveOrUpdateBodyPort;
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

public class SaveOrUpdateBodyPortTest {
    @Mock
    private MybatisBodyRepository mybatisBodyRepository;
    
    @Mock
    private BodyEntityMapper<MybatisBodyEntity> bodyEntityMapper;
    
    private SaveOrUpdateBodyPort underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new BodyRepository(mybatisBodyRepository, bodyEntityMapper);
    }
    
    @Test
    void save_shouldUpdateAndLoad() {
        
        Body body = mock(Body.class);
        MybatisBodyEntity entity = mock(MybatisBodyEntity.class);
        Body loaded = mock(Body.class);
        when(bodyEntityMapper.map(body)).thenReturn(entity);
        when(mybatisBodyRepository.insertOrUpdateOnConflict(any())).thenReturn(entity);
        when(bodyEntityMapper.map(entity)).thenReturn(loaded);
        
        
        Body result = underTest.saveOrUpdate(body);
        
        
        assertThat(result, is(loaded));
    }
}
