package io.edpn.backend.eddnrest.application.controller.station;

import io.edpn.backend.eddnrest.application.dto.station.GetStationResponse;
import io.edpn.backend.eddnrest.application.mapper.StationMapper;
import io.edpn.backend.eddnrest.application.usecase.GetStationUsecase;
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

    private final GetStationUsecase getStationUsecase;

    @GetMapping("/{id}")
    public ResponseEntity<GetStationResponse> getStation(@PathVariable UUID id) {
        return getStationUsecase.findById(id)
                .map(StationMapper::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
