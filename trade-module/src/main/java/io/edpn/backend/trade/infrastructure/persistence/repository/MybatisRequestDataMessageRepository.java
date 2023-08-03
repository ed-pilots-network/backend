package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
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
    public List<RequestDataMessage> findUnsend() {
        return requestDataMessageEntityMapper.findUnsend().stream()
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
