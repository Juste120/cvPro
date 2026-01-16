package juste.backend.services;
import juste.backend.dtos.requests.LoginRequest;
import juste.backend.dtos.requests.RegisterRequest;
import juste.backend.dtos.responses.JwtResponse;
import juste.backend.document.User;
import juste.backend.enums.Role;
import juste.backend.exceptions.ConflictException;
import juste.backend.repositories.UserRepository;
import juste.backend.securite.JwtTokenProvider;
import juste.backend.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123"
        );

        loginRequest = new LoginRequest(
                "john.doe@example.com",
                "password123"
        );

        user = User.builder()
                .id("user123")
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }

    @Test
    void register_WhenEmailNotExists_ShouldCreateUserAndReturnToken() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any())).thenReturn("jwt-token");

        // When
        JwtResponse response = authService.register(registerRequest);

        // Then
        assertNotNull(response);
        assertEquals("jwt-token", response.token());
        assertEquals("john.doe@example.com", response.email());
        assertEquals("John", response.firstName());
        assertEquals("Doe", response.lastName());

        verify(userRepository).existsByEmail("john.doe@example.com");
        verify(userRepository).save(any(User.class));
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void register_WhenEmailExists_ShouldThrowConflictException() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThrows(ConflictException.class, () -> {
            authService.register(registerRequest);
        });

        verify(userRepository).existsByEmail("john.doe@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_WhenCredentialsValid_ShouldReturnToken() {
        // Given
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any())).thenReturn("jwt-token");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // When
        JwtResponse response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertEquals("jwt-token", response.token());
        assertEquals("john.doe@example.com", response.email());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(authentication);
    }
}