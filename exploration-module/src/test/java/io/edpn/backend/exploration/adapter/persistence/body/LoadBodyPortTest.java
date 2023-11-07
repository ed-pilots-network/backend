package io.edpn.backend.exploration.adapter.persistence.body;

import io.edpn.backend.exploration.adapter.persistence.BodyRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisBodyRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.BodyEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.body.LoadBodyPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadBodyPortTest {
    @Mock
    private MybatisBodyRepository mybatisBodyRepository;
    
    @Mock
    private BodyEntityMapper<MybatisBodyEntity> bodyEntityMapper;
    
    private LoadBodyPort underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new BodyRepository(mybatisBodyRepository, bodyEntityMapper);
    }
    
    @Test
    void load_shouldFindByNameAndMap() {
        
        String name = "body";
        MybatisBodyEntity entity = mock(MybatisBodyEntity.class);
        Body mapped = mock(Body.class);
        when(mybatisBodyRepository.findByName(name)).thenReturn(Optional.of(entity));
        when(bodyEntityMapper.map(entity)).thenReturn(mapped);
        
        
        Optional<Body> result = underTest.load(name);
        
        
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(mapped));
    }
}
