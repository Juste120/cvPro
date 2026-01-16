package juste.backend.services.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import juste.backend.document.*;
import juste.backend.enums.Theme;
import juste.backend.exceptions.ResourceNotFoundException;
import juste.backend.repositories.CVRepository;
import juste.backend.services.IPDFExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PDFExportServiceImpl implements IPDFExportService {

    private final CVRepository cvRepository;
    private final MessageSource messageSource;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    @Override
    public byte[] generatePDF(String cvId, String userEmail, Locale locale) {
        log.info("Génération du PDF pour le CV: {} en langue: {}", cvId, locale.getLanguage());

        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new ResourceNotFoundException("CV", "id", cvId));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(document, baos);

            document.open();

            // Couleurs du CV
            String primaryColor = cv.getStyling() != null ? cv.getStyling().getPrimaryColor() : "#3B82F6";
            String accentColor = cv.getStyling() != null ? cv.getStyling().getAccentColor() : "#10B981";
            Theme theme = cv.getStyling() != null ? cv.getStyling().getTheme() : Theme.LIGHT;

            BaseColor primaryBaseColor = parseHexColor(primaryColor);
            BaseColor accentBaseColor = parseHexColor(accentColor);
            BaseColor textColor = theme == Theme.LIGHT ? BaseColor.BLACK : BaseColor.WHITE;

            // En-tête avec informations personnelles
            if (cv.getPersonalInfo() != null) {
                addPersonalInfo(document, cv.getPersonalInfo(), primaryBaseColor, textColor);
            }

            // Résumé professionnel
            if (cv.getSummary() != null && !cv.getSummary().isEmpty()) {
                addSection(document, getMessage("cv.summary", locale), cv.getSummary(),
                        primaryBaseColor, textColor);
            }

            // Expériences professionnelles
            if (cv.getExperiences() != null && !cv.getExperiences().isEmpty()) {
                addExperiences(document, cv.getExperiences(), locale, primaryBaseColor, textColor);
            }

            // Formation
            if (cv.getEducation() != null && !cv.getEducation().isEmpty()) {
                addEducation(document, cv.getEducation(), locale, primaryBaseColor, textColor);
            }

            // Compétences
            if (cv.getSkills() != null && !cv.getSkills().isEmpty()) {
                addSkills(document, cv.getSkills(), locale, primaryBaseColor, textColor);
            }

            // Langues
            if (cv.getLanguages() != null && !cv.getLanguages().isEmpty()) {
                addLanguages(document, cv.getLanguages(), locale, primaryBaseColor, textColor);
            }

            // Activités bénévoles
            if (cv.getVolunteerActivities() != null && !cv.getVolunteerActivities().isEmpty()) {
                addVolunteerActivities(document, cv.getVolunteerActivities(), locale,
                        primaryBaseColor, textColor);
            }

            // Centres d'intérêt
            if (cv.getInterests() != null && !cv.getInterests().isEmpty()) {
                addInterests(document, cv.getInterests(), locale, primaryBaseColor, textColor);
            }

            document.close();

            log.info("PDF généré avec succès pour le CV: {}", cvId);
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Erreur lors de la génération du PDF pour le CV: {}", cvId, e);
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    private void addPersonalInfo(Document document, PersonalInfo info,
                                 BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font nameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, textColor);
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, primaryColor);
        Font contactFont = FontFactory.getFont(FontFactory.HELVETICA, 10, textColor);

        Paragraph name = new Paragraph(info.getFullName(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        document.add(name);

        Paragraph jobTitle = new Paragraph(info.getJobTitle(), titleFont);
        jobTitle.setAlignment(Element.ALIGN_CENTER);
        jobTitle.setSpacingAfter(10);
        document.add(jobTitle);

        // Informations de contact
        StringBuilder contact = new StringBuilder();
        if (info.getEmail() != null) contact.append(info.getEmail()).append(" | ");
        if (info.getPhone() != null) contact.append(info.getPhone()).append(" | ");
        if (info.getAddress() != null) contact.append(info.getAddress());

        if (contact.length() > 0) {
            Paragraph contactPara = new Paragraph(contact.toString(), contactFont);
            contactPara.setAlignment(Element.ALIGN_CENTER);
            contactPara.setSpacingAfter(5);
            document.add(contactPara);
        }

        // LinkedIn et Skype
        StringBuilder socialMedia = new StringBuilder();
        if (info.getLinkedIn() != null) socialMedia.append("LinkedIn: ").append(info.getLinkedIn());
        if (info.getSkype() != null) {
            if (socialMedia.length() > 0) socialMedia.append(" | ");
            socialMedia.append("Skype: ").append(info.getSkype());
        }

        if (socialMedia.length() > 0) {
            Paragraph social = new Paragraph(socialMedia.toString(), contactFont);
            social.setAlignment(Element.ALIGN_CENTER);
            social.setSpacingAfter(20);
            document.add(social);
        }

        // Ligne de séparation
        LineSeparator line = new LineSeparator(1, 100, primaryColor, Element.ALIGN_CENTER, -2);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addSection(Document document, String title, String content,
                            BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, primaryColor);
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 11, textColor);

        Paragraph sectionTitle = new Paragraph(title.toUpperCase(), titleFont);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(5);
        document.add(sectionTitle);

        Paragraph contentPara = new Paragraph(content, contentFont);
        contentPara.setAlignment(Element.ALIGN_JUSTIFIED);
        contentPara.setSpacingAfter(10);
        document.add(contentPara);
    }

    private void addExperiences(Document document, java.util.List<Experience> experiences,
                                Locale locale, BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, primaryColor);
        Font positionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, textColor);
        Font companyFont = FontFactory.getFont(FontFactory.HELVETICA, 11, primaryColor);
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, textColor);
        Font descFont = FontFactory.getFont(FontFactory.HELVETICA, 10, textColor);

        Paragraph sectionTitle = new Paragraph(
                getMessage("cv.experience", locale).toUpperCase(), titleFont);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(5);
        document.add(sectionTitle);

        for (Experience exp : experiences) {
            Paragraph position = new Paragraph(exp.getPosition(), positionFont);
            document.add(position);

            String companyInfo = exp.getCompany();
            if (exp.getLocation() != null) companyInfo += " - " + exp.getLocation();
            Paragraph company = new Paragraph(companyInfo, companyFont);
            document.add(company);

            String dateRange = formatDateRange(exp.getStartDate(), exp.getEndDate(),
                    exp.getIsCurrent(), locale);
            Paragraph dates = new Paragraph(dateRange, dateFont);
            dates.setSpacingAfter(5);
            document.add(dates);

            if (exp.getDescription() != null) {
                Paragraph desc = new Paragraph(exp.getDescription(), descFont);
                desc.setSpacingAfter(5);
                document.add(desc);
            }

            if (exp.getAchievements() != null && !exp.getAchievements().isEmpty()) {
                com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
                list.setListSymbol("•");
                for (String achievement : exp.getAchievements()) {
                    list.add(new ListItem(achievement, descFont));
                }
                list.setIndentationLeft(20);
                document.add(list);
                document.add(Chunk.NEWLINE);
            } else {
                document.add(Chunk.NEWLINE);
            }
        }
    }

    private void addEducation(Document document, java.util.List<Education> education,
                              Locale locale, BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, primaryColor);
        Font degreeFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, textColor);
        Font institutionFont = FontFactory.getFont(FontFactory.HELVETICA, 11, primaryColor);
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, textColor);

        Paragraph sectionTitle = new Paragraph(
                getMessage("cv.education", locale).toUpperCase(), titleFont);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(5);
        document.add(sectionTitle);

        for (Education edu : education) {
            Paragraph degree = new Paragraph(edu.getDegree(), degreeFont);
            document.add(degree);

            String institutionInfo = edu.getInstitution();
            if (edu.getLocation() != null) institutionInfo += " - " + edu.getLocation();
            Paragraph institution = new Paragraph(institutionInfo, institutionFont);
            document.add(institution);

            String dateRange = formatDateRange(edu.getStartDate(), edu.getEndDate(),
                    false, locale);
            Paragraph dates = new Paragraph(dateRange, dateFont);
            dates.setSpacingAfter(10);
            document.add(dates);
        }
    }

    private void addSkills(Document document, java.util.List<Skill> skills,
                           Locale locale, BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, primaryColor);
        Font categoryFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, primaryColor);
        Font skillFont = FontFactory.getFont(FontFactory.HELVETICA, 10, textColor);

        Paragraph sectionTitle = new Paragraph(
                getMessage("cv.skills", locale).toUpperCase(), titleFont);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(5);
        document.add(sectionTitle);

        // Grouper par catégorie
        java.util.Map<String, java.util.List<Skill>> skillsByCategory = new java.util.LinkedHashMap<>();
        for (Skill skill : skills) {
            skillsByCategory.computeIfAbsent(skill.getCategory(), k -> new java.util.ArrayList<>()).add(skill);
        }

        for (java.util.Map.Entry<String, java.util.List<Skill>> entry : skillsByCategory.entrySet()) {
            Paragraph categoryPara = new Paragraph(entry.getKey() + ": ", categoryFont);

            String skillsList = entry.getValue().stream()
                    .map(s -> s.getName() + " (" + s.getLevel().name() + ")")
                    .collect(java.util.stream.Collectors.joining(", "));

            categoryPara.add(new Chunk(skillsList, skillFont));
            categoryPara.setSpacingAfter(5);
            document.add(categoryPara);
        }

        document.add(Chunk.NEWLINE);
    }

    private void addLanguages(Document document, java.util.List<Language> languages,
                              Locale locale, BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, primaryColor);
        Font langFont = FontFactory.getFont(FontFactory.HELVETICA, 10, textColor);

        Paragraph sectionTitle = new Paragraph(
                getMessage("cv.languages", locale).toUpperCase(), titleFont);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(5);
        document.add(sectionTitle);

        String languagesList = languages.stream()
                .map(l -> l.getName() + " (" + l.getLevel().name() + ")")
                .collect(java.util.stream.Collectors.joining(", "));

        Paragraph langPara = new Paragraph(languagesList, langFont);
        langPara.setSpacingAfter(10);
        document.add(langPara);
    }

    private void addVolunteerActivities(Document document, java.util.List<VolunteerActivity> activities,
                                        Locale locale, BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, primaryColor);
        Font roleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, textColor);
        Font orgFont = FontFactory.getFont(FontFactory.HELVETICA, 10, primaryColor);
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, textColor);
        Font descFont = FontFactory.getFont(FontFactory.HELVETICA, 10, textColor);

        Paragraph sectionTitle = new Paragraph(
                getMessage("cv.volunteer", locale).toUpperCase(), titleFont);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(5);
        document.add(sectionTitle);

        for (VolunteerActivity activity : activities) {
            Paragraph role = new Paragraph(activity.getRole(), roleFont);
            document.add(role);

            Paragraph org = new Paragraph(activity.getOrganization(), orgFont);
            document.add(org);

            String dateRange = formatDateRange(activity.getStartDate(), activity.getEndDate(),
                    activity.getIsCurrent(), locale);
            Paragraph dates = new Paragraph(dateRange, dateFont);
            dates.setSpacingAfter(5);
            document.add(dates);

            if (activity.getDescription() != null) {
                Paragraph desc = new Paragraph(activity.getDescription(), descFont);
                desc.setSpacingAfter(10);
                document.add(desc);
            }
        }
    }

    private void addInterests(Document document, java.util.List<String> interests,
                              Locale locale, BaseColor primaryColor, BaseColor textColor) throws DocumentException {

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, primaryColor);
        Font interestFont = FontFactory.getFont(FontFactory.HELVETICA, 10, textColor);

        Paragraph sectionTitle = new Paragraph(
                getMessage("cv.interests", locale).toUpperCase(), titleFont);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(5);
        document.add(sectionTitle);

        String interestsList = String.join(", ", interests);
        Paragraph interestPara = new Paragraph(interestsList, interestFont);
        interestPara.setSpacingAfter(10);
        document.add(interestPara);
    }

    private String formatDateRange(java.time.LocalDate start, java.time.LocalDate end,
                                   Boolean isCurrent, Locale locale) {
        String startStr = start.format(DATE_FORMATTER);
        String endStr = (isCurrent != null && isCurrent)
                ? getMessage("cv.present", locale)
                : (end != null ? end.format(DATE_FORMATTER) : "");
        return startStr + " - " + endStr;
    }

    private BaseColor parseHexColor(String hex) {
        if (hex == null || !hex.startsWith("#") || hex.length() != 7) {
            return BaseColor.BLUE;
        }
        try {
            int r = Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            return new BaseColor(r, g, b);
        } catch (Exception e) {
            return BaseColor.BLUE;
        }
    }

    private String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, code, locale);
    }
}