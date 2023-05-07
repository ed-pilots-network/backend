package io.edpn.backend.eddnrest.domain.model.system;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Coordinate {
    private double x;
    private double y;
    private double z;
}
