package io.edpn.backend.trade.adapter.persistence.entity;

import io.edpn.backend.trade.application.dto.persistence.entity.SystemDataRequestEntity;
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
public class MybatisSystemDataRequestEntity implements SystemDataRequestEntity {

    private String systemName;
}
