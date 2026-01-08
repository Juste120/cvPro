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
public class Preferences {
    private String language;      // fr | en
    private String defaultTheme;  // LIGHT | DARK
    private String defaultColor;  // #3B82F6
}
