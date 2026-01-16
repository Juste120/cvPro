package juste.backend.mappers;

import juste.backend.dtos.requests.*;
import juste.backend.dtos.responses.CVResponse;
import juste.backend.document.*;
import juste.backend.enums.LanguageLevel;
import juste.backend.enums.SkillLevel;
import juste.backend.enums.Theme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Slf4j
@Component
public class CVMapper {

    /**
     * Convertit un CV en CVResponse.
     */
    public CVResponse toResponse(CV cv) {
        if (cv == null) {
            return null;
        }

        return new CVResponse(
                cv.getId(),
                cv.getUserId(),
                cv.getTitle(),
                cv.getPersonalInfo(),
                cv.getSummary(),
                cv.getExperiences(),
                cv.getEducation(),
                cv.getSkills(),
                cv.getLanguages(),
                cv.getVolunteerActivities(),
                cv.getInterests(),
                cv.getStyling(),
                cv.getCreatedAt(),
                cv.getUpdatedAt()
        );
    }

    /**
     * Convertit un CVRequest en CV.
     */
    public CV toDocument(CVRequest request, String userId) {
        if (request == null) {
            return null;
        }

        return CV.builder()
                .userId(userId)
                .title(request.title())
                .personalInfo(toPersonalInfo(request.personalInfo()))
                .summary(request.summary())
                .experiences(toExperiences(request.experiences()))
                .education(toEducation(request.education()))
                .skills(toSkills(request.skills()))
                .languages(toLanguages(request.languages()))
                .volunteerActivities(toVolunteerActivities(request.volunteerActivities()))
                .interests(request.interests())
                .styling(toStyling(request.styling()))
                .build();
    }

    /**
     * Met à jour un CV existant avec les données d'une requête.
     */
    public void updateDocument(CV cv, CVRequest request) {
        if (cv == null || request == null) {
            return;
        }

        cv.setTitle(request.title());
        cv.setPersonalInfo(toPersonalInfo(request.personalInfo()));
        cv.setSummary(request.summary());
        cv.setExperiences(toExperiences(request.experiences()));
        cv.setEducation(toEducation(request.education()));
        cv.setSkills(toSkills(request.skills()));
        cv.setLanguages(toLanguages(request.languages()));
        cv.setVolunteerActivities(toVolunteerActivities(request.volunteerActivities()));
        cv.setInterests(request.interests());
        cv.setStyling(toStyling(request.styling()));
    }

    // Méthodes de conversion privées

    private PersonalInfo toPersonalInfo(PersonalInfoRequest request) {
        if (request == null) return null;

        return PersonalInfo.builder()
                .fullName(request.fullName())
                .jobTitle(request.jobTitle())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .linkedIn(request.linkedIn())
                .skype(request.skype())
                .build();
    }

    private List<Experience> toExperiences(List<ExperienceRequest> requests) {
        if (requests == null) return new ArrayList<>();

        return requests.stream()
                .map(req -> Experience.builder()
                        .position(req.position())
                        .company(req.company())
                        .location(req.location())
                        .startDate(req.startDate())
                        .endDate(req.endDate())
                        .isCurrent(req.isCurrent())
                        .description(req.description())
                        .achievements(req.achievements())
                        .build())
                .collect(Collectors.toList());
    }

    private List<Education> toEducation(List<EducationRequest> requests) {
        if (requests == null) return new ArrayList<>();

        return requests.stream()
                .map(req -> Education.builder()
                        .degree(req.degree())
                        .institution(req.institution())
                        .location(req.location())
                        .startDate(req.startDate())
                        .endDate(req.endDate())
                        .build())
                .collect(Collectors.toList());
    }

    private List<Skill> toSkills(List<SkillRequest> requests) {
        if (requests == null) return new ArrayList<>();

        return requests.stream()
                .map(req -> Skill.builder()
                        .name(req.name())
                        .category(req.category())
                        .level(SkillLevel.valueOf(req.level()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<Language> toLanguages(List<LanguageRequest> requests) {
        if (requests == null) return new ArrayList<>();

        return requests.stream()
                .map(req -> Language.builder()
                        .name(req.name())
                        .level(LanguageLevel.valueOf(req.level()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<VolunteerActivity> toVolunteerActivities(List<VolunteerActivityRequest> requests) {
        if (requests == null) return new ArrayList<>();

        return requests.stream()
                .map(req -> VolunteerActivity.builder()
                        .role(req.role())
                        .organization(req.organization())
                        .startDate(req.startDate())
                        .endDate(req.endDate())
                        .isCurrent(req.isCurrent())
                        .description(req.description())
                        .build())
                .collect(Collectors.toList());
    }

    private Styling toStyling(StylingRequest request) {
        if (request == null) return null;

        return Styling.builder()
                .theme(Theme.valueOf(request.theme()))
                .primaryColor(request.primaryColor())
                .accentColor(request.accentColor())
                .build();
    }
}
