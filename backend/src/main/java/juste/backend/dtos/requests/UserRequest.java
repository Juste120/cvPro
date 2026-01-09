package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Le prénom est obligatoire")
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank(message = "Le nom est obligatoire")
        @Size(min = 2, max = 50)
        String lastName,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        String email
) {}
