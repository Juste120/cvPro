package juste.backend.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import juste.backend.controllers.CVController;
import juste.backend.dtos.requests.CVRequest;
import juste.backend.dtos.requests.PersonalInfoRequest;
import juste.backend.dtos.requests.StylingRequest;
import juste.backend.dtos.responses.CVResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@WebMvcTest(CVController.class)
@AutoConfigureMockMvc(addFilters = false) // Désactiver la sécurité pour les tests
class CVControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ICVService cvService;

    private CVRequest cvRequest;
    private CVResponse cvResponse;

    @BeforeEach
    void setUp() {
        PersonalInfoRequest personalInfo = new PersonalInfoRequest(
                "John Doe",
                "Software Developer",
                "john@example.com",
                null, null, null, null
        );

        StylingRequest styling = new StylingRequest(
                "LIGHT",
                "#3B82F6",
                "#10B981"
        );

        cvRequest = new CVRequest(
                "CV Développeur",
                personalInfo,
                "Résumé",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                styling
        );

        cvResponse = new CVResponse(
                "cv123",
                "user123",
                "CV Développeur",
                null, null, null, null, null, null, null, null, null, null, null
        );
    }

    @Test
    @WithMockUser(username = "john@example.com")
    void getAllCVs_ShouldReturnCVList() throws Exception {
        // Given
        when(cvService.getAllByUser(anyString())).thenReturn(List.of(cvResponse));

        // When & Then
        mockMvc.perform(get("/api/cvs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("cv123"))
                .andExpect(jsonPath("$[0].title").value("CV Développeur"));
    }

    @Test
    @WithMockUser(username = "john@example.com")
    void createCV_WhenValidRequest_ShouldReturnCreated() throws Exception {
        // Given
        when(cvService.create(anyString(), any())).thenReturn(cvResponse);

        // When & Then
        mockMvc.perform(post("/api/cvs")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cvRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("cv123"));
    }

    @Test
    @WithMockUser(username = "john@example.com")
    void getCVById_WhenExists_ShouldReturnCV() throws Exception {
        // Given
        when(cvService.getById(anyString(), anyString())).thenReturn(cvResponse);

        // When & Then
        mockMvc.perform(get("/api/cvs/cv123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("cv123"));
    }

    @Test
    @WithMockUser(username = "john@example.com")
    void deleteCV_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/cvs/cv123")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}