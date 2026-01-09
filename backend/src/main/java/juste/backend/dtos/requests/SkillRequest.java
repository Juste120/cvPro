package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SkillRequest(
        @NotBlank(message = "Le nom de la compétence est obligatoire")
        String name,

        @NotBlank(message = "La catégorie est obligatoire")
        String category,

        @NotNull(message = "Le niveau est obligatoire")
        String level
) {}