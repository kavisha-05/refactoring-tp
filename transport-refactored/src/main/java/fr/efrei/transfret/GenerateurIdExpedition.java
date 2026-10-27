package fr.efrei.transfret;

import java.util.Random;

/**
 * Fabrique les identifiants d'expédition (EXP-numéro-aléa).
 */
public class GenerateurIdExpedition {

    private static final int ALEA_MIN = 1000;
    private static final int ALEA_BORNE = 9000;

    private final Random alea = new Random();
    private int compteur = 0;

    public String suivant() {
        compteur++;
        return "EXP-" + compteur + "-" + (ALEA_MIN + alea.nextInt(ALEA_BORNE));
    }
}
