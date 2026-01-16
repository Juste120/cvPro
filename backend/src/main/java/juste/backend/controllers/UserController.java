package juste.backend.controllers;
import jakarta.validation.Valid;
import juste.backend.dtos.requests.PreferencesRequest;
import juste.backend.dtos.requests.UpdatePasswordRequest;
import juste.backend.dtos.requests.UserRequest;
import juste.backend.dtos.responses.UserResponse;
import juste.backend.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * Récupère le profil de l'utilisateur connecté.
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(Authentication authentication) {
        log.info("GET /api/users/me - Utilisateur: {}", authentication.getName());
        UserResponse response = userService.getMe(authentication.getName());
        return ResponseEntity.ok(response);
    }

    /**
     * Met à jour le profil de l'utilisateur connecté.
     */
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(
            Authentication authentication,
            @Valid @RequestBody UserRequest request) {
        log.info("PUT /api/users/me - Utilisateur: {}", authentication.getName());
        UserResponse response = userService.updateMe(authentication.getName(), request);
        return ResponseEntity.ok(response);
    }

    /**
     * Met à jour les préférences de l'utilisateur connecté.
     */
    @PatchMapping("/me/preferences")
    public ResponseEntity<UserResponse> updatePreferences(
            Authentication authentication,
            @Valid @RequestBody PreferencesRequest request) {
        log.info("PATCH /api/users/me/preferences - Utilisateur: {}", authentication.getName());
        UserResponse response = userService.updatePreferences(authentication.getName(), request);
        return ResponseEntity.ok(response);
    }

    /**
     * Change le mot de passe de l'utilisateur connecté.
     */
    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            Authentication authentication,
            @Valid @RequestBody UpdatePasswordRequest request) {
        log.info("PATCH /api/users/me/password - Utilisateur: {}", authentication.getName());
        userService.updatePassword(authentication.getName(), request);
        return ResponseEntity.noContent().build();
    }

    /**
     * Supprime le compte de l'utilisateur connecté.
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(Authentication authentication) {
        log.info("DELETE /api/users/me - Utilisateur: {}", authentication.getName());
        userService.deleteMe(authentication.getName());
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère tous les utilisateurs (ADMIN uniquement).
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("GET /api/users - Admin");
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Récupère un utilisateur par son ID (ADMIN uniquement).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        log.info("GET /api/users/{} - Admin", id);
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Supprime un utilisateur par son ID (ADMIN uniquement).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("DELETE /api/users/{} - Admin", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
