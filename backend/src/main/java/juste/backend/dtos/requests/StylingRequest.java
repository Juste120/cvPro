package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record StylingRequest(
        @NotNull(message = "Le thème est obligatoire")
        String theme,

        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Format de couleur primaire invalide")
        String primaryColor,

        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Format de couleur d'accent invalide")
        String accentColor
) {}