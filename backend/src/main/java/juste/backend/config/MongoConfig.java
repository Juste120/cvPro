package juste.backend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.cvpro.repository")
@EnableMongoAuditing
public class MongoConfig {
}
