package juste.backend.controllers;

import jakarta.validation.Valid;
import juste.backend.dtos.requests.CVRequest;
import juste.backend.dtos.requests.UpdateStylingRequest;
import juste.backend.dtos.responses.CVResponse;
import juste.backend.services.ICVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Slf4j
@RestController
@RequestMapping("/api/cvs")
@RequiredArgsConstructor
public class CVController {

    private final ICVService cvService;

    /**
     * Récupère tous les CV de l'utilisateur connecté.
     */
    @GetMapping
    public ResponseEntity<List<CVResponse>> getAllCVs(Authentication authentication) {
        log.info("GET /api/cvs - Utilisateur: {}", authentication.getName());
        List<CVResponse> cvs = cvService.getAllByUser(authentication.getName());
        return ResponseEntity.ok(cvs);
    }

    /**
     * Crée un nouveau CV.
     */
    @PostMapping
    public ResponseEntity<CVResponse> createCV(
            Authentication authentication,
            @Valid @RequestBody CVRequest request) {
        log.info("POST /api/cvs - Utilisateur: {}", authentication.getName());
        CVResponse cv = cvService.create(authentication.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cv);
    }

    /**
     * Récupère un CV par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CVResponse> getCVById(
            Authentication authentication,
            @PathVariable String id) {
        log.info("GET /api/cvs/{} - Utilisateur: {}", id, authentication.getName());
        CVResponse cv = cvService.getById(authentication.getName(), id);
        return ResponseEntity.ok(cv);
    }

    /**
     * Met à jour un CV complet.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CVResponse> updateCV(
            Authentication authentication,
            @PathVariable String id,
            @Valid @RequestBody CVRequest request) {
        log.info("PUT /api/cvs/{} - Utilisateur: {}", id, authentication.getName());
        CVResponse cv = cvService.update(authentication.getName(), id, request);
        return ResponseEntity.ok(cv);
    }

    /**
     * Met à jour uniquement le styling d'un CV.
     */
    @PatchMapping("/{id}/styling")
    public ResponseEntity<CVResponse> updateStyling(
            Authentication authentication,
            @PathVariable String id,
            @Valid @RequestBody UpdateStylingRequest request) {
        log.info("PATCH /api/cvs/{}/styling - Utilisateur: {}", id, authentication.getName());
        CVResponse cv = cvService.updateStyling(authentication.getName(), id, request);
        return ResponseEntity.ok(cv);
    }

    /**
     * Supprime un CV.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCV(
            Authentication authentication,
            @PathVariable String id) {
        log.info("DELETE /api/cvs/{} - Utilisateur: {}", id, authentication.getName());
        cvService.delete(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Duplique un CV existant.
     */
    @PostMapping("/{id}/duplicate")
    public ResponseEntity<CVResponse> duplicateCV(
            Authentication authentication,
            @PathVariable String id) {
        log.info("POST /api/cvs/{}/duplicate - Utilisateur: {}", id, authentication.getName());
        CVResponse cv = cvService.duplicate(authentication.getName(), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(cv);
    }
}
