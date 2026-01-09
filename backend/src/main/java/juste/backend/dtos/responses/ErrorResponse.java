package juste.backend.dtos.responses;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, null);
    }

    public ErrorResponse(int status, String error, String message, String path, List<String> details) {
        this(LocalDateTime.now(), status, error, message, path, details);
    }
}