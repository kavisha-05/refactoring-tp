package fr.efrei.transfret;

/** Estime le délai de livraison, en jours. */
public class EstimateurDelai {
    private static final int DELAI_MINIMUM_JOURS = 1;

    public int estimer(int distance, ModeTransport mode, boolean urgent) {
        return Math.max(DELAI_MINIMUM_JOURS, mode.delai().estimer(distance, urgent));
    }
}
