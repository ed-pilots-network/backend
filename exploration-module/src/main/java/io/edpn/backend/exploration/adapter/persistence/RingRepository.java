package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisRingEntityMapper;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.port.outgoing.ring.SaveOrUpdateRingPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class RingRepository implements SaveOrUpdateRingPort {

    private final MybatisRingRepository ringRepository;
    private final MybatisRingEntityMapper ringEntityMapper;

    @Override
    public Ring saveOrUpdate(Ring ring) {
        return ringEntityMapper.map(ringRepository.insertOrUpdateOnConflict(ringEntityMapper.map(ring)));
    }
}
