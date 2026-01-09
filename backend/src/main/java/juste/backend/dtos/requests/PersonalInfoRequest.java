package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PersonalInfoRequest(
        @NotBlank(message = "Le nom complet est obligatoire")
        String fullName,

        @NotBlank(message = "Le titre du poste est obligatoire")
        String jobTitle,

        @Email(message = "Format d'email invalide")
        String email,

        String phone,
        String address,
        String linkedIn,
        String skype
) {}
