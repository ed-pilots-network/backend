package io.edpn.backend.messageprocessor.navroutev1.application.dto.persistence;

import lombok.*;

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
    private Long eliteId;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
    private String starClass;

}



