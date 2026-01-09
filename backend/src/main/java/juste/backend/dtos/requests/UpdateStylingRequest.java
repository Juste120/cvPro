package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateStylingRequest(
        @NotNull(message = "Le thème est obligatoire")
        @Pattern(regexp = "^(LIGHT|DARK)$", message = "Thème invalide (LIGHT ou DARK)")
        String theme,

        @NotNull(message = "La couleur primaire est obligatoire")
        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Format de couleur primaire invalide")
        String primaryColor,

        @NotNull(message = "La couleur d'accent est obligatoire")
        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Format de couleur d'accent invalide")
        String accentColor
) {}