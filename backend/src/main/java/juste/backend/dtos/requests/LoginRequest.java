package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        String password
) {}