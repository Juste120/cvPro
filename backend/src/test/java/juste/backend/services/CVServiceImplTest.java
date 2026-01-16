package juste.backend.services;

import juste.backend.dtos.requests.CVRequest;
import juste.backend.dtos.requests.PersonalInfoRequest;
import juste.backend.dtos.requests.StylingRequest;
import juste.backend.dtos.responses.CVResponse;
import juste.backend.document.CV;
import juste.backend.document.PersonalInfo;
import juste.backend.document.Styling;
import juste.backend.document.User;
import juste.backend.enums.Role;
import juste.backend.enums.Theme;
import juste.backend.exceptions.UnauthorizedException;
import juste.backend.mappers.CVMapper;
import juste.backend.repositories.CVRepository;
import juste.backend.repositories.UserRepository;
import juste.backend.services.impl.CVServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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
class CVServiceImplTest {

    @Mock
    private CVRepository cvRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CVMapper cvMapper;

    @InjectMocks
    private CVServiceImpl cvService;

    private User user;
    private CV cv;
    private CVRequest cvRequest;
    private CVResponse cvResponse;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id("user123")
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .role(Role.USER)
                .build();

        PersonalInfo personalInfo = PersonalInfo.builder()
                .fullName("John Doe")
                .jobTitle("Software Developer")
                .email("john.doe@example.com")
                .build();

        Styling styling = Styling.builder()
                .theme(Theme.LIGHT)
                .primaryColor("#3B82F6")
                .accentColor("#10B981")
                .build();

        cv = CV.builder()
                .id("cv123")
                .userId("user123")
                .title("CV Développeur")
                .personalInfo(personalInfo)
                .summary("Développeur passionné")
                .styling(styling)
                .experiences(new ArrayList<>())
                .education(new ArrayList<>())
                .skills(new ArrayList<>())
                .languages(new ArrayList<>())
                .build();

        PersonalInfoRequest personalInfoRequest = new PersonalInfoRequest(
                "John Doe",
                "Software Developer",
                "john.doe@example.com",
                null, null, null, null
        );

        StylingRequest stylingRequest = new StylingRequest(
                "LIGHT",
                "#3B82F6",
                "#10B981"
        );

        cvRequest = new CVRequest(
                "CV Développeur",
                personalInfoRequest,
                "Développeur passionné",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                stylingRequest
        );

        cvResponse = new CVResponse(
                "cv123",
                "user123",
                "CV Développeur",
                personalInfo,
                "Développeur passionné",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                styling,
                null,
                null
        );
    }

    @Test
    void create_WhenUserExists_ShouldCreateCV() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(cvMapper.toDocument(any(), anyString())).thenReturn(cv);
        when(cvRepository.save(any(CV.class))).thenReturn(cv);
        when(cvMapper.toResponse(any())).thenReturn(cvResponse);

        // When
        CVResponse result = cvService.create("john.doe@example.com", cvRequest);

        // Then
        assertNotNull(result);
        assertEquals("CV Développeur", result.title());
        assertEquals("user123", result.userId());

        verify(userRepository).findByEmail("john.doe@example.com");
        verify(cvRepository).save(any(CV.class));
    }

    @Test
    void getById_WhenCVExistsAndUserIsOwner_ShouldReturnCV() {
        // Given
        when(cvRepository.findById(anyString())).thenReturn(Optional.of(cv));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(cvMapper.toResponse(any())).thenReturn(cvResponse);

        // When
        CVResponse result = cvService.getById("john.doe@example.com", "cv123");

        // Then
        assertNotNull(result);
        assertEquals("cv123", result.id());

        verify(cvRepository).findById("cv123");
    }

    @Test
    void getById_WhenUserIsNotOwner_ShouldThrowUnauthorizedException() {
        // Given
        User anotherUser = User.builder()
                .id("user456")
                .email("another@example.com")
                .build();

        when(cvRepository.findById(anyString())).thenReturn(Optional.of(cv));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(anotherUser));

        // When & Then
        assertThrows(UnauthorizedException.class, () -> {
            cvService.getById("another@example.com", "cv123");
        });
    }

    @Test
    void getAllByUser_ShouldReturnUserCVs() {
        // Given
        List<CV> cvList = List.of(cv);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(cvRepository.findByUserIdOrderByCreatedAtDesc(anyString())).thenReturn(cvList);
        when(cvMapper.toResponse(any())).thenReturn(cvResponse);

        // When
        List<CVResponse> result = cvService.getAllByUser("john.doe@example.com");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(cvRepository).findByUserIdOrderByCreatedAtDesc("user123");
    }

    @Test
    void delete_WhenUserIsOwner_ShouldDeleteCV() {
        // Given
        when(cvRepository.findById(anyString())).thenReturn(Optional.of(cv));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // When
        cvService.delete("john.doe@example.com", "cv123");

        // Then
        verify(cvRepository).delete(cv);
    }

    @Test
    void duplicate_WhenCVExists_ShouldCreateDuplicate() {
        // Given
        CV duplicatedCV = CV.builder()
                .id("cv456")
                .userId("user123")
                .title("CV Développeur (Copie)")
                .build();

        when(cvRepository.findById(anyString())).thenReturn(Optional.of(cv));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(cvRepository.save(any(CV.class))).thenReturn(duplicatedCV);
        when(cvMapper.toResponse(any())).thenReturn(cvResponse);

        // When
        CVResponse result = cvService.duplicate("john.doe@example.com", "cv123");

        // Then
        assertNotNull(result);
        verify(cvRepository).save(any(CV.class));
    }
}