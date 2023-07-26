package io.edpn.backend.exploration.infrastructure.persistence.repository;

import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MybatisRequestDataMessageRepository implements RequestDataMessageRepository {

    private final RequestDataMessageEntityMapper requestDataMessageEntityMapper;
    private final RequestDataMessageMapper requestDataMessageMapper;

    @Override
    public Optional<RequestDataMessage> find(RequestDataMessage requestDataMessage) {
        return requestDataMessageEntityMapper.find(requestDataMessageMapper.map(requestDataMessage))
                .map(requestDataMessageMapper::map);
    }

    @Override
    public void create(RequestDataMessage requestDataMessage) {
        requestDataMessageEntityMapper.insert(requestDataMessageMapper.map(requestDataMessage));
    }

    @Override
    public List<RequestDataMessage> findNotSend() {
        return requestDataMessageEntityMapper.findNotSend().stream()
                .map(requestDataMessageMapper::map)
                .toList();
    }

    @Override
    public void setSend(RequestDataMessage requestDataMessage) {
        requestDataMessageEntityMapper.find(requestDataMessageMapper.map(requestDataMessage))
                .ifPresent(requestDataMessageEntity -> {
                    requestDataMessageEntity.setSend(true);
                    requestDataMessageEntityMapper.update(requestDataMessageEntity);
                });
    }
}
