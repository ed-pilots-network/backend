package io.edpn.backend.exploration.infrastructure.persistence.mappers.entity;

import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.infrastructure.persistence.entity.RequestDataMessageEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestDataMessageMapper {

    public RequestDataMessageEntity map(RequestDataMessage requestDataMessage) {
        String sanitizedTopicName = this.sanitizeTopicName(requestDataMessage.getTopic());
        return RequestDataMessageEntity.builder()
                .topic(sanitizedTopicName)
                .message(requestDataMessage.getMessage())
                .build();
    }

    public RequestDataMessage map(RequestDataMessageEntity requestDataMessage) {
        return RequestDataMessage.builder()
                .topic(requestDataMessage.getTopic())
                .message(requestDataMessage.getMessage())
                .build();
    }

    private String sanitizeTopicName(String topicName) {
        return topicName.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }


}