package fr.efrei.transfret;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExtensionFluvialeTest {
    private Colis colis(double l, double w, double h, TypeMarchandise type) {
        return new Colis("Test", 100, l, w, h, type, 100);
    }

    @Test void tarifDelaiEtPeniche() {
        TarifFluvial tarif = new TarifFluvial();
        assertEquals(300, tarif.calculer(365, 100, 0), 0.00001);
        assertEquals(346, tarif.calculer(365, 10000, 0), 0.00001);
        assertEquals(2, new EstimateurDelai().estimer(360, ModeTransport.FLUVIAL, false));
        assertEquals(3, new EstimateurDelai().estimer(365, ModeTransport.FLUVIAL, false));

        Application app = new Application(true);
        Client client = app.serviceClient().creerClient("Jean", "jean@example.fr", "0600000000", "Paris", TypeClient.PARTICULIER, null);
        Expedition e = app.serviceExpedition().creerExpedition(client, List.of(colis(100, 100, 100, TypeMarchandise.STANDARD)), "Paris", "Le Havre", ModeTransport.FLUVIAL, false, false);
        assertNotNull(e);
        assertEquals("PEN-0001", e.getVehicule().getImmatriculation());
        assertEquals(2500000, e.getVehicule().getCapaciteMax());
        assertEquals(300, e.getPrixHT(), 0.00001);
        assertTrue(app.serviceExpedition().demarrerExpedition(e.getId()));
        assertTrue(app.serviceExpedition().livrerExpedition(e.getId()));
    }

    @Test void interdictionsFluviales() {
        Application app = new Application(true);
        Client client = app.serviceClient().creerClient("Jean", "jean@example.fr", "0600000000", "Paris", TypeClient.PARTICULIER, null);
        assertNull(app.serviceExpedition().creerExpedition(client, List.of(colis(1, 1, 1, TypeMarchandise.REFRIGERE)), "Paris", "Le Havre", ModeTransport.FLUVIAL, false, false));
        assertNull(app.serviceExpedition().creerExpedition(client, List.of(colis(1, 1, 1, TypeMarchandise.STANDARD)), "Paris", "Le Havre", ModeTransport.FLUVIAL, true, false));
    }

    @Test void supplementEtLimite() {
        assertEquals(110, new SupplementHorsGabarit(() -> 100).calculer(), 0.00001);
        CalculateurPrix actif = new CalculateurPrix(new AssemblagePrix(true));
        CalculateurPrix historique = new CalculateurPrix(new AssemblagePrix(false));
        assertEquals(130, actif.calculerPrixHT(List.of(colis(200, 200, 200, TypeMarchandise.STANDARD)), 100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER), 0.00001);
        for (Colis grand : List.of(colis(201, 1, 1, TypeMarchandise.STANDARD), colis(1, 201, 1, TypeMarchandise.STANDARD), colis(1, 1, 201, TypeMarchandise.STANDARD))) {
            assertEquals(143, actif.calculerPrixHT(List.of(grand), 100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER), 0.00001);
            assertEquals(130, historique.calculerPrixHT(List.of(grand), 100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER), 0.00001);
        }
    }

    @Test void penicheRespecteLaCapaciteEtLeSupplementPrecedeLaRemiseEtLAssurance() {
        Peniche peniche = new Peniche("PEN-0001", 2500000);
        AffectationVehicule affectation = new AffectationVehicule(new Flotte(List.of(peniche)));
        Colis limite = new Colis("limite", 2500000, 1, 1, 1, TypeMarchandise.STANDARD, 100);
        Colis tropLourd = new Colis("trop lourd", 2500001, 1, 1, 1, TypeMarchandise.STANDARD, 100);
        assertSame(peniche, affectation.trouverVehicule(List.of(limite), ModeTransport.FLUVIAL));
        assertNull(affectation.trouverVehicule(List.of(tropLourd), ModeTransport.FLUVIAL));

        CalculateurPrix calculateur = new CalculateurPrix(new AssemblagePrix(true));
        Colis grand = new Colis("grand", 100, 201, 10, 10, TypeMarchandise.STANDARD, 1000);
        // Base route 130, hors gabarit 143, remise 20 %, assurance 20.
        assertEquals(134.4, calculateur.calculerPrixHT(List.of(grand), 100, ModeTransport.ROUTE, false, true, TypeClient.PREMIUM), 0.00001);
    }
}
