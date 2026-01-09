package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VolunteerActivityRequest(
        @NotBlank(message = "Le rôle est obligatoire")
        String role,

        @NotBlank(message = "L'organisation est obligatoire")
        String organization,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate startDate,

        LocalDate endDate,
        Boolean isCurrent,
        String description
) {}