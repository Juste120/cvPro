package juste.backend.services;

import juste.backend.dtos.requests.PreferencesRequest;
import juste.backend.dtos.requests.UpdatePasswordRequest;
import juste.backend.dtos.requests.UserRequest;
import juste.backend.dtos.responses.UserResponse;

import java.util.List;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public interface IUserService {

    /**
     * Récupère le profil de l'utilisateur connecté.
     */
    UserResponse getMe(String email);

    /**
     * Met à jour le profil de l'utilisateur connecté.
     */
    UserResponse updateMe(String email, UserRequest request);

    /**
     * Met à jour les préférences de l'utilisateur connecté.
     */
    UserResponse updatePreferences(String email, PreferencesRequest request);

    /**
     * Change le mot de passe de l'utilisateur connecté.
     */
    void updatePassword(String email, UpdatePasswordRequest request);

    /**
     * Supprime le compte de l'utilisateur connecté.
     */
    void deleteMe(String email);

    /**
     * Récupère tous les utilisateurs (ADMIN uniquement).
     */
    List<UserResponse> getAllUsers();

    /**
     * Récupère un utilisateur par son ID (ADMIN uniquement).
     */
    UserResponse getUserById(String id);

    /**
     * Supprime un utilisateur par son ID (ADMIN uniquement).
     */
    void deleteUser(String id);
}