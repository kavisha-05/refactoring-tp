package fr.efrei.transfret;

import java.util.Optional;

/**
 * Règles de validation d'un nouveau client.
 */
public class ValidateurClient {

    private static final int LONGUEUR_SIRET = 14;

    /** @return le message d'erreur, ou vide si la demande est valide */
    public Optional<String> verifier(String nom, String email, TypeClient type, String siret) {
        if (nom == null || nom.trim().length() == 0) {
            return Optional.of("nom obligatoire");
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return Optional.of("email invalide (" + email + ")");
        }
        if (type == null) {
            return Optional.of("type de client inconnu");
        }
        if (type == TypeClient.ENTREPRISE && (siret == null || siret.length() != LONGUEUR_SIRET)) {
            return Optional.of("SIRET obligatoire (" + LONGUEUR_SIRET + " chiffres) pour une entreprise");
        }
        return Optional.empty();
    }
}
