package fr.efrei.transfret;

public interface EnvoiEmail {

    /** @return true si l'email est parti */
    boolean envoyer(String destinataire, String sujet, String corps);
}
