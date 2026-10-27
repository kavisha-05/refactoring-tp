package fr.efrei.transfret;

import java.util.Optional;

/**
 * Crée un client : valide la demande, l'enregistre, puis envoie l'email de bienvenue.
 */
public class ServiceClient {

    private final ValidateurClient validateur = new ValidateurClient();
    private final Notificateur notificateur = new Notificateur();

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        Optional<String> erreur = validateur.verifier(nom, email, type, siret);
        if (erreur.isPresent()) {
            System.out.println("ERREUR : " + erreur.get());
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        Database.getInstance().sauvegarderClient(c);
        notificateur.envoyerEmail(c, "Bienvenue chez TransFret", "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }
}
