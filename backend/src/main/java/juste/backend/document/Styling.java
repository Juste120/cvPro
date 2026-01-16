package juste.backend.document;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */
import juste.backend.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Styling {
    private Theme theme;
    private String primaryColor;
    private String accentColor;
}