package io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence;

import java.time.LocalDateTime;
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
public class SchemaLatestTimestampEntity {

    private String schema;
    private LocalDateTime timestamp;
}
