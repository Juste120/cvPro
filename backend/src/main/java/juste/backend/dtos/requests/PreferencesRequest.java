package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PreferencesRequest(
        @NotBlank(message = "La langue est obligatoire")
        @Pattern(regexp = "^(fr|en)$", message = "Langue invalide (fr ou en)")
        String language,

        @NotBlank(message = "Le thème par défaut est obligatoire")
        @Pattern(regexp = "^(LIGHT|DARK)$", message = "Thème invalide (LIGHT ou DARK)")
        String defaultTheme,

        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Format de couleur invalide (format: #RRGGBB)")
        String defaultColor
) {}