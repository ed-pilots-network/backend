package io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationTypeEntity {

    private UUID id;
    private String name;
}



