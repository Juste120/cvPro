package juste.backend.services;

import juste.backend.dtos.requests.CVRequest;
import juste.backend.dtos.requests.UpdateStylingRequest;
import juste.backend.dtos.responses.CVResponse;

import java.util.List;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public interface ICVService {

    /**
     * Crée un nouveau CV.
     */
    CVResponse create(String userEmail, CVRequest request);

    /**
     * Récupère un CV par son ID.
     */
    CVResponse getById(String userEmail, String cvId);

    /**
     * Récupère tous les CV de l'utilisateur connecté.
     */
    List<CVResponse> getAllByUser(String userEmail);

    /**
     * Met à jour un CV complet.
     */
    CVResponse update(String userEmail, String cvId, CVRequest request);

    /**
     * Met à jour uniquement le styling d'un CV.
     */
    CVResponse updateStyling(String userEmail, String cvId, UpdateStylingRequest request);

    /**
     * Supprime un CV.
     */
    void delete(String userEmail, String cvId);

    /**
     * Duplique un CV existant.
     */
    CVResponse duplicate(String userEmail, String cvId);
}