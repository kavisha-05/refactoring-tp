package fr.efrei.transfret;

import java.util.List;

public class Peniche extends Vehicule implements VehiculeMotorise {
    public Peniche(String immatriculation, double capaciteMax) { super(immatriculation, capaciteMax); }
    public boolean accepte(List<Colis> colis, ModeTransport mode) {
        return mode == ModeTransport.FLUVIAL && colis.stream().noneMatch(c -> c.getType().estRefrigere());
    }
    public void partir(int distance) {
        faireLePlein();
        System.out.println("La péniche " + immatriculation + " navigue sur " + distance + " km");
    }
}
