package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ExperienceRequest(
        @NotBlank(message = "Le poste est obligatoire")
        String position,

        @NotBlank(message = "L'entreprise est obligatoire")
        String company,

        String location,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate startDate,

        LocalDate endDate,
        Boolean isCurrent,
        String description,
        List<String> achievements
) {}
