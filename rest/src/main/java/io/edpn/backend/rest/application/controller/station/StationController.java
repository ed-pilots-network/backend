package io.edpn.backend.rest.application.controller.station;

import io.edpn.backend.rest.application.dto.station.FindStationResponse;
import io.edpn.backend.rest.application.mapper.station.StationMapper;
import io.edpn.backend.rest.application.usecase.station.FindStationUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final FindStationUseCase getStationUsecase;

    @GetMapping("/{id}")
    public ResponseEntity<FindStationResponse> getStation(@PathVariable UUID id) {
        return getStationUsecase.findById(id)
                .map(StationMapper::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
