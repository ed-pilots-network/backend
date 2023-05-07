package io.edpn.backend.rest.domain.model.station;

import lombok.Builder;

@Builder
public record Commodity(Long id, String name){
}
