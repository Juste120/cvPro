package juste.backend.services.impl;

import juste.backend.dtos.requests.CVRequest;
import juste.backend.dtos.requests.UpdateStylingRequest;
import juste.backend.dtos.responses.CVResponse;
import juste.backend.document.CV;
import juste.backend.document.Styling;
import juste.backend.document.User;
import juste.backend.enums.Theme;
import juste.backend.exceptions.ResourceNotFoundException;
import juste.backend.exceptions.UnauthorizedException;
import juste.backend.mappers.CVMapper;
import juste.backend.repositories.CVRepository;
import juste.backend.repositories.UserRepository;
import juste.backend.services.ICVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CVServiceImpl implements ICVService {

    private final CVRepository cvRepository;
    private final UserRepository userRepository;
    private final CVMapper cvMapper;

    @Override
    @Transactional
    public CVResponse create(String userEmail, CVRequest request) {
        log.info("Création d'un nouveau CV pour l'utilisateur: {}", userEmail);

        User user = findUserByEmail(userEmail);
        CV cv = cvMapper.toDocument(request, user.getId());

        CV savedCV = cvRepository.save(cv);
        log.info("CV créé avec succès, ID: {}", savedCV.getId());

        return cvMapper.toResponse(savedCV);
    }

    @Override
    @Transactional(readOnly = true)
    public CVResponse getById(String userEmail, String cvId) {
        log.debug("Récupération du CV {} pour l'utilisateur: {}", cvId, userEmail);

        CV cv = findCVById(cvId);
        verifyOwnership(cv, userEmail);

        return cvMapper.toResponse(cv);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CVResponse> getAllByUser(String userEmail) {
        log.debug("Récupération de tous les CV pour l'utilisateur: {}", userEmail);

        User user = findUserByEmail(userEmail);
        List<CV> cvs = cvRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return cvs.stream()
                .map(cvMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CVResponse update(String userEmail, String cvId, CVRequest request) {
        log.info("Mise à jour du CV {} pour l'utilisateur: {}", cvId, userEmail);

        CV cv = findCVById(cvId);
        verifyOwnership(cv, userEmail);

        cvMapper.updateDocument(cv, request);

        CV updatedCV = cvRepository.save(cv);
        log.info("CV mis à jour avec succès: {}", cvId);

        return cvMapper.toResponse(updatedCV);
    }

    @Override
    @Transactional
    public CVResponse updateStyling(String userEmail, String cvId, UpdateStylingRequest request) {
        log.info("Mise à jour du styling du CV {} pour l'utilisateur: {}", cvId, userEmail);

        CV cv = findCVById(cvId);
        verifyOwnership(cv, userEmail);

        Styling styling = Styling.builder()
                .theme(Theme.valueOf(request.theme()))
                .primaryColor(request.primaryColor())
                .accentColor(request.accentColor())
                .build();

        cv.setStyling(styling);

        CV updatedCV = cvRepository.save(cv);
        log.info("Styling du CV mis à jour avec succès: {}", cvId);

        return cvMapper.toResponse(updatedCV);
    }

    @Override
    @Transactional
    public void delete(String userEmail, String cvId) {
        log.info("Suppression du CV {} pour l'utilisateur: {}", cvId, userEmail);

        CV cv = findCVById(cvId);
        verifyOwnership(cv, userEmail);

        cvRepository.delete(cv);
        log.info("CV supprimé avec succès: {}", cvId);
    }

    @Override
    @Transactional
    public CVResponse duplicate(String userEmail, String cvId) {
        log.info("Duplication du CV {} pour l'utilisateur: {}", cvId, userEmail);

        CV originalCV = findCVById(cvId);
        verifyOwnership(originalCV, userEmail);

        // Créer une copie du CV
        CV duplicatedCV = CV.builder()
                .userId(originalCV.getUserId())
                .title(originalCV.getTitle() + " (Copie)")
                .personalInfo(originalCV.getPersonalInfo())
                .summary(originalCV.getSummary())
                .experiences(originalCV.getExperiences())
                .education(originalCV.getEducation())
                .skills(originalCV.getSkills())
                .languages(originalCV.getLanguages())
                .volunteerActivities(originalCV.getVolunteerActivities())
                .interests(originalCV.getInterests())
                .styling(originalCV.getStyling())
                .build();

        CV savedCV = cvRepository.save(duplicatedCV);
        log.info("CV dupliqué avec succès, nouvel ID: {}", savedCV.getId());

        return cvMapper.toResponse(savedCV);
    }

    /**
     * Méthode utilitaire pour récupérer un CV par son ID.
     */
    private CV findCVById(String cvId) {
        return cvRepository.findById(cvId)
                .orElseThrow(() -> new ResourceNotFoundException("CV", "id", cvId));
    }

    /**
     * Méthode utilitaire pour récupérer un utilisateur par email.
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "email", email));
    }

    /**
     * Vérifie que l'utilisateur est propriétaire du CV.
     */
    private void verifyOwnership(CV cv, String userEmail) {
        User user = findUserByEmail(userEmail);

        if (!cv.getUserId().equals(user.getId())) {
            log.warn("Tentative d'accès non autorisé au CV {} par l'utilisateur: {}", cv.getId(), userEmail);
            throw new UnauthorizedException("Vous n'êtes pas autorisé à accéder à ce CV");
        }
    }
}
