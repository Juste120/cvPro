package juste.backend.repositories;

import juste.backend.entities.CV;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Repository
public interface CVRepository extends MongoRepository<CV, String> {


    List<CV> findByUserId(String userId);


    List<CV> findByUserIdOrderByCreatedAtDesc(String userId);


    boolean existsByIdAndUserId(String id, String userId);


    Optional<CV> findByIdAndUserId(String id, String userId);
}