package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Envoi de SMS simulé : écrit sur la console et garde une trace des messages (facturés par l'opérateur).
 */
public class SmsConsole implements EnvoiSms {

    private static final int LONGUEUR_MAX = 160;
    private static final String SUFFIXE_TRONCATURE = "...";

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyer(String numero, String message) {
        if (message.length() > LONGUEUR_MAX) {
            message = message.substring(0, LONGUEUR_MAX - SUFFIXE_TRONCATURE.length()) + SUFFIXE_TRONCATURE;
        }
        System.out.println("[SMS] -> " + numero + " : " + message);
        journal.add(new String[]{numero, message});
    }

    /** Chaque entrée : numéro, message. */
    public List<String[]> getJournal() {
        return Collections.unmodifiableList(journal);
    }
}
