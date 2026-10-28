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
            dangereux |= c.getType().estDangereux();
            refrigere |= c.getType().estRefrigere();
        }
        if (dangereux && !mode.accepteDangereux()) {
            return Optional.of("les matières dangereuses sont interdites en transport aérien");
        }
        if (refrigere && !mode.accepteRefrigere()) {
            return Optional.of("le transport réfrigéré n'est possible que par route ou par mer");
        }
        if (urgent && mode.erreurUrgence() != null) {
            return Optional.of(mode.erreurUrgence());
        }
        return Optional.empty();
    }
}
