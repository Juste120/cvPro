package juste.backend.exceptions;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}