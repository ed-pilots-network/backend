package io.edpn.backend.rest.application.controller.system;

import io.edpn.backend.rest.application.dto.system.GetSystemResponse;
import io.edpn.backend.rest.application.mapper.system.SystemMapper;
import io.edpn.backend.rest.application.usecase.system.GetSystemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/systems")
@RequiredArgsConstructor
public class SystemController {
    
    private final GetSystemUseCase getSystemUseCase;
    
    @GetMapping("/{id}")
    public ResponseEntity<GetSystemResponse> getSystem(@PathVariable UUID id) {
        return getSystemUseCase.findById(id)
                .map(SystemMapper::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
