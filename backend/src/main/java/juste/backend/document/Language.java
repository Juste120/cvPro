package juste.backend.document;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */
import juste.backend.enums.LanguageLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    private String name;
    private LanguageLevel level;
}