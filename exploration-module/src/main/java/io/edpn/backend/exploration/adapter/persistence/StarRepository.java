package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.application.domain.Star;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StarEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.star.SaveOrUpdateStarPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StarRepository implements SaveOrUpdateStarPort {

    private final MybatisStarRepository mybatisStarRepository;
    private final StarEntityMapper<MybatisStarEntity> starEntityMapper;

    @Override
    public Star saveOrUpdate(Star star) {
        return starEntityMapper.map(mybatisStarRepository.insertOrUpdateOnConflict(starEntityMapper.map(star)));
    }
}
