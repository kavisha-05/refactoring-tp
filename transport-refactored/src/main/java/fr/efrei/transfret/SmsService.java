package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class SmsService {

    // garde une trace des SMS envoyés (facturés par l'opérateur)
    public static List<String[]> journal = new ArrayList<String[]>();

    private static final int LONGUEUR_MAX = 160;
    private static final String SUFFIXE_TRONCATURE = "...";

    private String apiKey = "sk_live_8f7d6e5c4b3a2918";

    public void envoyerSms(String numero, String message) {
        if (message.length() > LONGUEUR_MAX) {
            message = message.substring(0, LONGUEUR_MAX - SUFFIXE_TRONCATURE.length()) + SUFFIXE_TRONCATURE;
        }
        System.out.println("[SMS] -> " + numero + " : " + message);
        journal.add(new String[]{numero, message});
    }
}
