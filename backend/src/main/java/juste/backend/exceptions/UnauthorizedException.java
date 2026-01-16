package juste.backend.exceptions;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}