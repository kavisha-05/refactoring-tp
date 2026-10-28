package fr.efrei.transfret;

import java.util.List;

public class AffectationVehicule {
    private final Flotte flotte;
    public AffectationVehicule(Flotte flotte) { this.flotte = flotte; }

    public Vehicule trouverVehicule(List<Colis> colis, ModeTransport mode) {
        double poids = 0;
        for (Colis c : colis) poids += c.getPoids();
        for (Vehicule vehicule : flotte.getVehicules()) {
            if (vehicule.estDisponible() && vehicule.getCapaciteMax() - vehicule.getChargeActuelle() >= poids
                    && vehicule.accepte(colis, mode)) return vehicule;
        }
        return null;
    }
}