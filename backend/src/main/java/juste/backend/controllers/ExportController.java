package juste.backend.controllers;

import juste.backend.services.IPDFExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Locale;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Slf4j
@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final IPDFExportService pdfExportService;

    /**
     * Génère et télécharge un CV au format PDF.
     *
     * @param cvId ID du CV à exporter
     * @param lang Langue du PDF (fr ou en)
     * @param authentication Authentification de l'utilisateur
     * @return Fichier PDF à télécharger
     */
    @GetMapping("/pdf/{cvId}")
    public ResponseEntity<byte[]> exportToPDF(
            @PathVariable String cvId,
            @RequestParam(defaultValue = "fr") String lang,
            Authentication authentication) {

        log.info("GET /api/export/pdf/{} - Utilisateur: {}, Langue: {}",
                cvId, authentication.getName(), lang);

        // Déterminer la locale
        Locale locale = "en".equalsIgnoreCase(lang) ? Locale.ENGLISH : Locale.FRENCH;

        // Générer le PDF
        byte[] pdfContent = pdfExportService.generatePDF(cvId, authentication.getName(), locale);

        // Créer le nom du fichier
        String fileName = generateFileName();

        // Préparer les headers HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        log.info("PDF généré avec succès: {}", fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

    /**
     * Génère le nom du fichier PDF avec la date du jour.
     */
    private String generateFileName() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return String.format("CV_%s.pdf", date);
    }
}
