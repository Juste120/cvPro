package juste.backend.controllers;

import jakarta.validation.Valid;
import juste.backend.dtos.requests.LoginRequest;
import juste.backend.dtos.requests.RegisterRequest;
import juste.backend.dtos.responses.JwtResponse;
import juste.backend.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService authService;

    /**
     * Inscription d'un nouvel utilisateur.
     *
     * @param request Données d'inscription
     * @return JWT token et informations utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("POST /api/auth/register - Inscription pour: {}", request.email());
        JwtResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Connexion d'un utilisateur existant.
     *
     * @param request Identifiants de connexion
     * @return JWT token et informations utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("POST /api/auth/login - Connexion pour: {}", request.email());
        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
