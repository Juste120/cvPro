package juste.backend.services;

import java.util.Locale;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
public interface IPDFExportService {

    /**
     * Génère un PDF pour un CV donné.
     *
     * @param cvId ID du CV
     * @param userEmail Email de l'utilisateur propriétaire
     * @param locale Locale pour la traduction
     * @return Contenu du PDF en bytes
     */
    byte[] generatePDF(String cvId, String userEmail, Locale locale);
}
