package juste.backend.dtos.responses;

/**
 * @author PAKOU Komi Juste
 * @since 1/9/26
 */

import juste.backend.entities.Preferences;

public record UserResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        String role,
        Preferences preferences
) {}