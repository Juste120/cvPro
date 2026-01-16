package juste.backend.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * Secret pour signer les tokens JWT.
     */
    private String secret;

    /**
     * Durée de validité du token en millisecondes (par défaut 24h).
     */
    private Long expiration = 86400000L;
}
