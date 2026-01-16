package juste.backend.document;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cvs")
public class CV {

    @Id
    private String id;

    @Indexed
    private String userId;

    private String title;
    private PersonalInfo personalInfo;
    private String summary;
    private List<Experience> experiences;
    private List<Education> education;
    private List<Skill> skills;
    private List<Language> languages;
    private List<VolunteerActivity> volunteerActivities;
    private List<String> interests;
    private Styling styling;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}