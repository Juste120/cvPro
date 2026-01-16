package juste.backend.document;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    private String degree;
    private String institution;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
}
