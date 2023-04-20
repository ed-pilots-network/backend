package io.eddb.eddb2backend.application.dto.persistence;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemEntity {

    private UUID id;
    private String name;

}



