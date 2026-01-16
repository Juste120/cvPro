package juste.backend.services;

import juste.backend.dtos.requests.LoginRequest;
import juste.backend.dtos.requests.RegisterRequest;
import juste.backend.dtos.responses.JwtResponse;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public interface IAuthService {

    /**
     * Inscription d'un nouvel utilisateur.
     */
    JwtResponse register(RegisterRequest request);

    /**
     * Connexion d'un utilisateur existant.
     */
    JwtResponse login(LoginRequest request);
}
