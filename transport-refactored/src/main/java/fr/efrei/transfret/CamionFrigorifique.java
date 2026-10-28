package fr.efrei.transfret;

import java.util.List;

public class CamionFrigorifique extends Vehicule implements VehiculeMotorise {
    private static final double TEMPERATURE = 4;
    public CamionFrigorifique(String immatriculation, double capaciteMax) { super(immatriculation, capaciteMax); }
    public boolean accepte(List<Colis> colis, ModeTransport mode) {
        return mode == ModeTransport.ROUTE && colis.stream().allMatch(c -> c.getType().estRefrigere());
    }
    public void partir(int distance) {
        faireLePlein();
        System.out.println("Le camion " + immatriculation + " roule sur " + distance + " km");
        System.out.println("Réfrigération activée à " + TEMPERATURE + "°C sur " + immatriculation);
    }
}
