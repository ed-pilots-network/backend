package io.edpn.backend.exploration.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Ring{
    private UUID id;
    private Long innerRadius;
    private Long mass; // MT MegaTonnes
    private String name;
    private Long outerRadius;
    private String ringClass;
    private Body body;
    private Star star;
}