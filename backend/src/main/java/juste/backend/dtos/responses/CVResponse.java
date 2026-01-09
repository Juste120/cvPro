package juste.backend.dtos.responses;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import juste.backend.entities.*;

import java.time.LocalDateTime;
import java.util.List;

public record CVResponse(
        String id,
        String userId,
        String title,
        PersonalInfo personalInfo,
        String summary,
        List<Experience> experiences,
        List<Education> education,
        List<Skill> skills,
        List<Language> languages,
        List<VolunteerActivity> volunteerActivities,
        List<String> interests,
        Styling styling,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
