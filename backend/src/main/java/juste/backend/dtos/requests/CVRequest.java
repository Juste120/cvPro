package juste.backend.dtos.requests;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CVRequest(
        @NotBlank(message = "Le titre du CV est obligatoire")
        @Size(max = 100, message = "Le titre ne peut pas dépasser 100 caractères")
        String title,

        @Valid
        PersonalInfoRequest personalInfo,

        @Size(max = 1000, message = "Le résumé ne peut pas dépasser 1000 caractères")
        String summary,

        List<@Valid ExperienceRequest> experiences,
        List<@Valid EducationRequest> education,
        List<@Valid SkillRequest> skills,
        List<@Valid LanguageRequest> languages,
        List<@Valid VolunteerActivityRequest> volunteerActivities,
        List<String> interests,

        @Valid
        StylingRequest styling
) {}
