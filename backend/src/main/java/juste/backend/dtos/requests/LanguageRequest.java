package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LanguageRequest(
        @NotBlank(message = "Le nom de la langue est obligatoire")
        String name,

        @NotNull(message = "Le niveau est obligatoire")
        String level // NATIVE | EXPERT | CONVERSATIONAL | BEGINNER
) {}
