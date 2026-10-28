package fr.efrei.transfret;

/**
 * Envoie les notifications aux clients (email, et SMS pour les clients PREMIUM).
 */
public class Notificateur {

    private final EnvoiEmail email;
    private final EnvoiSms sms;

    public Notificateur(EnvoiEmail email, EnvoiSms sms) {
        this.email = email;
        this.sms = sms;
    }

    public void envoyerEmail(Client client, String sujet, String corps) {
        email.envoyer(client.getEmail(), sujet, corps);
    }

    public void notifier(Client client, String sujet, String corps, String messageSms) {
        envoyerEmail(client, sujet, corps);
        if (client.getType().notificationSms()) {
            sms.envoyer(client.getTelephone(), messageSms);
        }
    }
}
