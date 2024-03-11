package io.edpn.backend.exploration.adapter.persistence.star;

import io.edpn.backend.exploration.adapter.persistence.MybatisStarRepository;
import io.edpn.backend.exploration.adapter.persistence.StarRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStarEntityMapper;
import io.edpn.backend.exploration.application.domain.Star;
import io.edpn.backend.exploration.application.port.outgoing.star.SaveOrUpdateStarPort;
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

public class SaveOrUpdateStarPortTest {
    @Mock
    private MybatisStarRepository mybatisStarRepository;

    @Mock
    private MybatisStarEntityMapper starEntityMapper;

    private SaveOrUpdateStarPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StarRepository(mybatisStarRepository, starEntityMapper);
    }

    @Test
    void save_shouldUpdateAndLoad() {

        Star star = mock(Star.class);
        MybatisStarEntity entity = mock(MybatisStarEntity.class);
        Star loaded = mock(Star.class);
        when(starEntityMapper.map(star)).thenReturn(entity);
        when(mybatisStarRepository.insertOrUpdateOnConflict(any())).thenReturn(entity);
        when(starEntityMapper.map(entity)).thenReturn(loaded);


        Star result = underTest.saveOrUpdate(star);


        assertThat(result, is(loaded));
    }
}
