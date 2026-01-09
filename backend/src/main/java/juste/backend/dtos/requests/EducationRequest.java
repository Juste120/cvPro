package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EducationRequest(
        @NotBlank(message = "Le diplôme est obligatoire")
        String degree,

        @NotBlank(message = "L'établissement est obligatoire")
        String institution,

        String location,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate startDate,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate endDate
) {}