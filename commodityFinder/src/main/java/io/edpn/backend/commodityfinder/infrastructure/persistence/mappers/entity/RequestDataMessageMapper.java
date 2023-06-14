package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity;

import io.edpn.backend.commodityfinder.domain.model.RequestDataMessage;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.RequestDataMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
public class RequestDataMessageMapper {

    public RequestDataMessageEntity map(RequestDataMessage requestDataMessage) {
        String sanitizedTopicName = this.sanitizeTopicName(requestDataMessage.getTopic());
        return RequestDataMessageEntity.builder().topic(sanitizedTopicName).message(requestDataMessage.getMessage().asText()).build();
    }

    private String sanitizeTopicName(String topicName) {
        return topicName.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }

}
