package fr.efrei.transfret;

/**
 * Envoie les notifications aux clients (email, et SMS pour les clients PREMIUM).
 */
public class Notificateur {

    public void envoyerEmail(Client client, String sujet, String corps) {
        EmailService.envoyer(client.getEmail(), sujet, corps);
    }

    public void notifier(Client client, String sujet, String corps, String sms) {
        envoyerEmail(client, sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            new SmsService().envoyerSms(client.getTelephone(), sms);
        }
    }
}
