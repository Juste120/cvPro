package juste.backend.entities;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
    private String fullName;
    private String jobTitle;
    private String email;
    private String phone;
    private String address;
    private String linkedIn;
    private String skype;
}