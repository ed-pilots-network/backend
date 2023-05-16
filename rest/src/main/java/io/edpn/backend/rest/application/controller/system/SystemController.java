package io.edpn.backend.rest.application.controller.system;

import io.edpn.backend.rest.application.dto.system.FindSystemResponse;
import io.edpn.backend.rest.application.mapper.system.SystemMapper;
import io.edpn.backend.rest.application.usecase.system.FindSystemUseCase;
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
    
    private final FindSystemUseCase findSystemUseCase;
    
    @GetMapping("/{id}")
    public ResponseEntity<FindSystemResponse> getSystem(@PathVariable UUID id) {
        return findSystemUseCase.findById(id)
                .map(SystemMapper::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
