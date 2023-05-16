package io.edpn.backend.rest.domain.model.common;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Government {
    private UUID id;
    private String name;
}
