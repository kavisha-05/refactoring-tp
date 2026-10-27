package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Envoi d'emails simulé : écrit sur la console et garde une trace des messages (demandé par le support client).
 */
public class EmailConsole implements EnvoiEmail {

    private static final String SMTP_HOST = "smtp.transfret.fr";
    private static final String EXPEDITEUR = "noreply@transfret.fr";

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public boolean envoyer(String destinataire, String sujet, String corps) {
        if (destinataire == null || destinataire.indexOf("@") < 0) {
            return false;
        }
        System.out.println("[EMAIL via " + SMTP_HOST + "] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
        System.out.println("        " + corps);
        journal.add(new String[]{destinataire, sujet, corps});
        return true;
    }

    /** Chaque entrée : destinataire, sujet, corps. */
    public List<String[]> getJournal() {
        return Collections.unmodifiableList(journal);
    }
}
