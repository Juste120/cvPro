package juste.backend.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Endpoints de test")
public class TestController {

    @GetMapping("/hello")
    @Operation(summary = "Test simple", description = "Endpoint de test pour vérifier que l'API fonctionne")
    public ResponseEntity<Map<String, Object>> hello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello CVPro! L'API fonctionne correctement.");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "OK");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifier l'état de l'application")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "CVPro Backend");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}

