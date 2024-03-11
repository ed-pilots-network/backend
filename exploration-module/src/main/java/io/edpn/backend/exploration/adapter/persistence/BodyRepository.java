package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisBodyEntityMapper;
import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.port.outgoing.body.LoadBodyPort;
import io.edpn.backend.exploration.application.port.outgoing.body.SaveOrUpdateBodyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class BodyRepository implements SaveOrUpdateBodyPort, LoadBodyPort {

    private final MybatisBodyRepository mybatisBodyRepository;
    private final MybatisBodyEntityMapper bodyEntityMapper;

    @Override
    public Optional<Body> load(String name) {
        return mybatisBodyRepository.findByName(name)
                .map(bodyEntityMapper::map);
    }

    @Override
    public Body saveOrUpdate(Body body) {
        return bodyEntityMapper.map(mybatisBodyRepository.insertOrUpdateOnConflict(bodyEntityMapper.map(body)));
    }
}
