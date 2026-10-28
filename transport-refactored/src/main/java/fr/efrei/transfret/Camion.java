package fr.efrei.transfret;

import java.util.List;

public class Camion extends Vehicule implements VehiculeMotorise {
    private final boolean adr;
    public Camion(String immatriculation, double capaciteMax, boolean adr) {
        super(immatriculation, capaciteMax);
        this.adr = adr;
    }
    public boolean accepte(List<Colis> colis, ModeTransport mode) {
        return mode == ModeTransport.ROUTE
                && colis.stream().noneMatch(c -> c.getType().estRefrigere())
                && (adr || colis.stream().noneMatch(c -> c.getType().estDangereux()));
    }
    public void partir(int distance) {
        faireLePlein();
        System.out.println("Le camion " + immatriculation + " roule sur " + distance + " km");
    }
}
