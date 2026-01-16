package juste.backend.services.impl;

import juste.backend.dtos.requests.LoginRequest;
import juste.backend.dtos.requests.RegisterRequest;
import juste.backend.dtos.responses.JwtResponse;
import juste.backend.document.Preferences;
import juste.backend.document.User;
import juste.backend.enums.Role;
import juste.backend.exceptions.ConflictException;
import juste.backend.repositories.UserRepository;
import juste.backend.securite.JwtTokenProvider;
import juste.backend.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public JwtResponse register(RegisterRequest request) {
        log.info("Tentative d'inscription pour l'email: {}", request.email());

        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Un compte existe déjà avec cet email");
        }

        // Créer les préférences par défaut
        Preferences preferences = Preferences.builder()
                .language("fr")
                .defaultTheme("LIGHT")
                .defaultColor("#3B82F6")
                .build();

        // Créer le nouvel utilisateur
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .role(Role.USER)
                .preferences(preferences)
                .build();

        userRepository.save(user);
        log.info("Utilisateur inscrit avec succès: {}", user.getEmail());

        // Authentifier automatiquement l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(
                token,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        );
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        log.info("Tentative de connexion pour l'email: {}", request.email());

        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = jwtTokenProvider.generateToken(authentication);

        // Récupérer l'utilisateur pour les détails
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        log.info("Utilisateur connecté avec succès: {}", user.getEmail());

        return new JwtResponse(
                token,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        );
    }
}

