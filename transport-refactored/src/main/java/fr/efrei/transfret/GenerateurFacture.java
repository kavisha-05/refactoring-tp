package fr.efrei.transfret;

import java.util.Date;

/**
 * Met en forme la facture d'une expédition.
 */
public class GenerateurFacture {

    private static final String SEPARATEUR_DOUBLE = "==============================================\n";
    private static final String SEPARATEUR_SIMPLE = "----------------------------------------------\n";

    public String generer(Expedition expedition) {
        Client client = expedition.getClient();
        StringBuilder sb = new StringBuilder();
        sb.append(SEPARATEUR_DOUBLE);
        sb.append("              FACTURE TRANSFRET\n");
        sb.append(SEPARATEUR_DOUBLE);
        sb.append("Facture n° F-" + expedition.getId() + "\n");
        sb.append("Date : " + Utils.formaterDate(new Date()) + "\n");
        sb.append("Client : " + client.getNom() + "\n");
        if (client.getType().siretRequis()) {
            sb.append("SIRET : " + client.getSiret() + "\n");
        }
        sb.append("Adresse : " + client.getAdresse() + "\n");
        sb.append(SEPARATEUR_SIMPLE);
        sb.append("Trajet : " + expedition.getVilleDepart() + " -> " + expedition.getVilleArrivee() + " (" + expedition.getDistance() + " km)\n");
        sb.append("Mode : " + expedition.getMode() + (expedition.isUrgent() ? " (URGENT)" : "") + "\n");
        sb.append("Colis :\n");
        for (Colis c : expedition.getColis()) {
            sb.append("  - " + c.getDescription() + " : " + c.getPoids() + " kg [" + c.getLibelleType() + "]\n");
        }
        sb.append(SEPARATEUR_SIMPLE);
        if (expedition.getStatut() == StatutExpedition.ANNULEE) {
            sb.append("EXPEDITION ANNULEE\n");
            sb.append("Frais d'annulation TTC : " + String.format("%.2f", expedition.getFraisAnnulation()) + " €\n");
        } else {
            sb.append("Total HT  : " + String.format("%.2f", expedition.getPrixHT()) + " €\n");
            sb.append("TVA 20%   : " + String.format("%.2f", expedition.getPrixTTC() - expedition.getPrixHT()) + " €\n");
            sb.append("Total TTC : " + String.format("%.2f", expedition.getPrixTTC()) + " €\n");
        }
        sb.append(SEPARATEUR_DOUBLE);
        return sb.toString();
    }
}
