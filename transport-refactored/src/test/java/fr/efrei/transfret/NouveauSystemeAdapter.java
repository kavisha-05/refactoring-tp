package fr.efrei.transfret;

import fr.efrei.transport.contrat.Canal;
import fr.efrei.transport.contrat.ColisDto;
import fr.efrei.transport.contrat.DemandeClient;
import fr.efrei.transport.contrat.DemandeDevis;
import fr.efrei.transport.contrat.DemandeExpedition;
import fr.efrei.transport.contrat.EtatExpedition;
import fr.efrei.transport.contrat.Notification;
import fr.efrei.transport.contrat.Statut;
import fr.efrei.transport.contrat.SystemeTransport;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Branche le nouveau code sur les tests de contrat.
 * <p>
 * Ne fait que de la traduction : conversion des types du contrat vers ceux du domaine,
 * et accès aux journaux de notifications pour les tests.
 */
public class NouveauSystemeAdapter implements SystemeTransport {

    private Application app;

    @Override
    public void reinitialiser() {
        app = new Application();
    }

    @Override
    public Optional<String> creerClient(DemandeClient d) {
        Client client = app.serviceClient().creerClient(d.nom(), d.email(), d.telephone(), d.adresse(), TypeClient.valueOf(d.type().name()), d.siret());
        return Optional.ofNullable(client).map(Client::getEmail);
    }

    @Override
    public Optional<String> creerExpedition(DemandeExpedition d) {
        Client client = app.clients().trouverParEmail(d.refClient()).orElse(null);
        Expedition expedition = app.serviceExpedition().creerExpedition(client, versColis(d.colis()), d.villeDepart(),
                d.villeArrivee(), ModeTransport.valueOf(d.mode().name()), d.urgent(), d.assurance());
        return Optional.ofNullable(expedition).map(Expedition::getId);
    }

    @Override
    public Optional<EtatExpedition> consulter(String id) {
        Expedition e = app.expeditions().trouver(id).orElse(null);
        if (e == null) {
            return Optional.empty();
        }
        int delai = (int) ChronoUnit.DAYS.between(jour(e.getDateCreation()), jour(e.getDateLivraisonPrevue()));
        return Optional.of(new EtatExpedition(e.getId(), Statut.valueOf(e.getStatut().name()), e.getPrixHT(), e.getPrixTTC(),
                delai, e.getVehicule().getImmatriculation(), e.getFraisAnnulation()));
    }

    @Override
    public OptionalDouble devisTTC(DemandeDevis d) {
        double devis = app.serviceExpedition().calculerDevis(versColis(d.colis()), d.villeDepart(), d.villeArrivee(),
                ModeTransport.valueOf(d.mode().name()), d.urgent(), d.assurance(), TypeClient.valueOf(d.typeClient().name()));
        return devis == -1 ? OptionalDouble.empty() : OptionalDouble.of(devis);
    }

    @Override
    public boolean demarrer(String id) {
        return app.serviceExpedition().demarrerExpedition(id);
    }

    @Override
    public boolean livrer(String id) {
        return app.serviceExpedition().livrerExpedition(id);
    }

    @Override
    public boolean annuler(String id) {
        return app.serviceExpedition().annulerExpedition(id);
    }

    @Override
    public Optional<String> facture(String id) {
        return app.expeditions().trouver(id).map(app.generateurFacture()::generer);
    }

    @Override
    public List<Notification> notifications() {
        List<Notification> notifications = new ArrayList<>();
        for (String[] email : app.emails().getJournal()) {
            notifications.add(new Notification(Canal.EMAIL, email[0], email[1]));
        }
        for (String[] sms : app.sms().getJournal()) {
            notifications.add(new Notification(Canal.SMS, sms[0], sms[1]));
        }
        return notifications;
    }

    private static java.time.LocalDate jour(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static List<Colis> versColis(List<ColisDto> colis) {
        return colis.stream().map(NouveauSystemeAdapter::versColis).collect(Collectors.toCollection(ArrayList::new));
    }

    private static Colis versColis(ColisDto c) {
        return new Colis(c.description(), c.poidsKg(), c.longueurCm(), c.largeurCm(), c.hauteurCm(),
                TypeMarchandise.valueOf(c.type().name()), c.valeurDeclaree());
    }
}
