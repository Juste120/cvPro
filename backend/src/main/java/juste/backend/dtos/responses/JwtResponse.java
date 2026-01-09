package juste.backend.dtos.responses;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */
public record JwtResponse(
        String token,
        String type,
        String email,
        String firstName,
        String lastName,
        String role
) {
    public JwtResponse(String token, String email, String firstName, String lastName, String role) {
        this(token, "Bearer", email, firstName, lastName, role);
    }
}
