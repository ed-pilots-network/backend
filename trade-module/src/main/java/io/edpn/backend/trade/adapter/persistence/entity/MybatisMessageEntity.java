package io.edpn.backend.trade.adapter.persistence.entity;

import io.edpn.backend.trade.application.dto.persistence.entity.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MybatisMessageEntity implements MessageEntity {
    private String message;
    private String topic;
}
