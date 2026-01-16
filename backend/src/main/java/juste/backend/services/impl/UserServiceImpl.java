package juste.backend.services.impl;

import juste.backend.dtos.requests.PreferencesRequest;
import juste.backend.dtos.requests.UpdatePasswordRequest;
import juste.backend.dtos.requests.UserRequest;
import juste.backend.dtos.responses.UserResponse;
import juste.backend.document.Preferences;
import juste.backend.document.User;
import juste.backend.exceptions.BadRequestException;
import juste.backend.exceptions.ConflictException;
import juste.backend.exceptions.ResourceNotFoundException;
import juste.backend.mappers.UserMapper;
import juste.backend.repositories.UserRepository;
import juste.backend.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getMe(String email) {
        log.debug("Récupération du profil pour: {}", email);

        User user = findUserByEmail(email);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateMe(String email, UserRequest request) {
        log.info("Mise à jour du profil pour: {}", email);

        User user = findUserByEmail(email);

        // Vérifier si le nouvel email existe déjà (si différent)
        if (!email.equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Un compte existe déjà avec cet email");
        }

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());

        User updatedUser = userRepository.save(user);
        log.info("Profil mis à jour avec succès pour: {}", updatedUser.getEmail());

        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional
    public UserResponse updatePreferences(String email, PreferencesRequest request) {
        log.info("Mise à jour des préférences pour: {}", email);

        User user = findUserByEmail(email);

        Preferences preferences = Preferences.builder()
                .language(request.language())
                .defaultTheme(request.defaultTheme())
                .defaultColor(request.defaultColor())
                .build();

        user.setPreferences(preferences);

        User updatedUser = userRepository.save(user);
        log.info("Préférences mises à jour pour: {}", email);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional
    public void updatePassword(String email, UpdatePasswordRequest request) {
        log.info("Changement de mot de passe pour: {}", email);

        User user = findUserByEmail(email);

        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new BadRequestException("L'ancien mot de passe est incorrect");
        }

        // Encoder et sauvegarder le nouveau mot de passe
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        log.info("Mot de passe changé avec succès pour: {}", email);
    }

    @Override
    @Transactional
    public void deleteMe(String email) {
        log.info("Suppression du compte pour: {}", email);

        User user = findUserByEmail(email);
        userRepository.delete(user);

        log.info("Compte supprimé avec succès pour: {}", email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.debug("Récupération de tous les utilisateurs");

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(String id) {
        log.debug("Récupération de l'utilisateur avec ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        log.info("Suppression de l'utilisateur avec ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        userRepository.delete(user);
        log.info("Utilisateur supprimé avec succès: {}", id);
    }

    /**
     * Méthode utilitaire pour récupérer un utilisateur par email.
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "email", email));
    }
}