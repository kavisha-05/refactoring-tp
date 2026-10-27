package fr.efrei.transfret;

import java.util.List;
import java.util.Optional;

/**
 * Règles d'acceptation d'une expédition (hors trajet et véhicule, qui dépendent d'autres données).
 */
public class ValidateurExpedition {

    /** @return le message d'erreur, ou vide si la demande est acceptable */
    public Optional<String> verifier(Client client, List<Colis> colis, ModeTransport mode, boolean urgent) {
        if (client == null) {
            return Optional.of("client obligatoire");
        }
        if (colis == null || colis.isEmpty()) {
            return Optional.of("au moins un colis est nécessaire");
        }
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            if (c.getPoids() <= 0) {
                return Optional.of("poids invalide pour " + c.getDescription());
            }
            if (c.getType() == TypeMarchandise.DANGEREUX) dangereux = true;
            if (c.getType() == TypeMarchandise.REFRIGERE) refrigere = true;
        }
        if (dangereux && mode == ModeTransport.AIR) {
            return Optional.of("les matières dangereuses sont interdites en transport aérien");
        }
        if (refrigere && !(mode == ModeTransport.ROUTE || mode == ModeTransport.MER)) {
            return Optional.of("le transport réfrigéré n'est possible que par route ou par mer");
        }
        if (urgent && mode == ModeTransport.MER) {
            return Optional.of("pas d'option urgente pour le transport maritime");
        }
        return Optional.empty();
    }
}
