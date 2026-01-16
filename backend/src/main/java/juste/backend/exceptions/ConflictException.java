package juste.backend.exceptions;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}