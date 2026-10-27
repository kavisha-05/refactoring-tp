package fr.efrei.transfret;

/**
 * Estime le délai de livraison, en jours.
 */
public class EstimateurDelai {

    private static final double VITESSE_ROUTE_KMH = 70.0;
    private static final int HEURES_CONDUITE_PAR_JOUR = 9;
    private static final int HEURES_CONDUITE_PAR_JOUR_URGENT = 18;
    private static final double KM_PAR_JOUR_RAIL = 1440.0;
    private static final int JOURS_SUPPLEMENTAIRES_RAIL = 1;
    private static final int REDUCTION_URGENCE_RAIL_JOURS = 1;
    private static final double KM_PAR_JOUR_MER = 720.0;
    private static final int JOURS_SUPPLEMENTAIRES_MER = 2;
    private static final int DISTANCE_LONGUE_AIR_KM = 3000;
    private static final int JOURS_AIR = 1;
    private static final int JOURS_AIR_LONGUE_DISTANCE = 2;
    private static final int DELAI_MINIMUM_JOURS = 1;

    // retourne le délai de livraison en jours
    public int estimer(int distance, ModeTransport mode, boolean urgent) {
        int jours = 0;
        switch (mode) {
            case ROUTE:
                double heures = distance / VITESSE_ROUTE_KMH;
                if (urgent) {
                    jours = (int) Math.ceil(heures / HEURES_CONDUITE_PAR_JOUR_URGENT);
                } else {
                    jours = (int) Math.ceil(heures / HEURES_CONDUITE_PAR_JOUR);
                }
                break;
            case RAIL:
                jours = (int) Math.ceil(distance / KM_PAR_JOUR_RAIL) + JOURS_SUPPLEMENTAIRES_RAIL;
                if (urgent) jours -= REDUCTION_URGENCE_RAIL_JOURS;
                break;
            case MER:
                jours = (int) Math.ceil(distance / KM_PAR_JOUR_MER) + JOURS_SUPPLEMENTAIRES_MER;
                break;
            case AIR:
                jours = JOURS_AIR;
                if (distance > DISTANCE_LONGUE_AIR_KM) jours = JOURS_AIR_LONGUE_DISTANCE;
                if (urgent) jours = JOURS_AIR;
                break;
        }
        if (jours < DELAI_MINIMUM_JOURS) jours = DELAI_MINIMUM_JOURS;
        return jours;
    }
}
