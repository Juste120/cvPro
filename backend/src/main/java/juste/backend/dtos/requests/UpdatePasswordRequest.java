package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank(message = "L'ancien mot de passe est obligatoire")
        String oldPassword,

        @NotBlank(message = "Le nouveau mot de passe est obligatoire")
        @Size(min = 8, message = "Le nouveau mot de passe doit contenir au moins 8 caractères")
        String newPassword
) {}