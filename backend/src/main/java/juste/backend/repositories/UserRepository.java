package juste.backend.repositories;

import juste.backend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Recherche un utilisateur par email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà.
     */
    boolean existsByEmail(String email);
}