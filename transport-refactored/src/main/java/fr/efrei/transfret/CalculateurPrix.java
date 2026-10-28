package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {

    // suppléments
    private static final double COEF_FRAGILE = 1.15;
    private static final double COEF_DANGEREUX = 1.30;
    private static final double COEF_REFRIGERE = 1.20;
    private static final double COEF_URGENCE = 1.5;

    // assurance
    private static final double TAUX_ASSURANCE = 0.02;
    private static final double MINIMUM_ASSURANCE = 15;

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        double poids = 0;
        double volume = 0;
        double valeur = 0;
        boolean fragile = false;
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            poids = poids + c.getPoids();
            volume = volume + c.getVolume();
            valeur = valeur + c.getValeurDeclaree();
            if (c.getType() == TypeMarchandise.FRAGILE) fragile = true;
            if (c.getType() == TypeMarchandise.DANGEREUX) dangereux = true;
            if (c.getType() == TypeMarchandise.REFRIGERE) refrigere = true;
        }

        double prix = mode.tarif().calculer(distance, poids, volume);

        // suppléments
        if (fragile) prix = prix * COEF_FRAGILE;
        if (dangereux) prix = prix * COEF_DANGEREUX;
        if (refrigere) prix = prix * COEF_REFRIGERE;
        if (urgent) prix = prix * COEF_URGENCE;

        prix = typeClient.remise().appliquer(prix);

        // assurance
        if (assurance) {
            double montantAssurance = valeur * TAUX_ASSURANCE;
            if (montantAssurance < MINIMUM_ASSURANCE) montantAssurance = MINIMUM_ASSURANCE;
            prix = prix + montantAssurance;
        }
        return Utils.arrondir(prix);
    }
}
