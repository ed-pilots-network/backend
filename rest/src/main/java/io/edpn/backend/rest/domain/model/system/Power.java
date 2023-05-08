package io.edpn.backend.rest.domain.model.system;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Power{
    private UUID id;
    private String name;
}
