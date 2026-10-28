package fr.efrei.transfret;

import java.util.List;

public class Avion extends Vehicule implements VehiculeMotorise {
    private static final int ALTITUDE_CROISIERE = 35000;
    public Avion(String immatriculation, double capaciteMax) { super(immatriculation, capaciteMax); }
    public boolean accepte(List<Colis> colis, ModeTransport mode) { return mode == ModeTransport.AIR; }
    public void partir(int distance) {
        faireLePlein();
        System.out.println("L'avion " + immatriculation + " roule sur la piste");
        System.out.println("L'avion " + immatriculation + " vole à " + ALTITUDE_CROISIERE + " pieds");
    }
}
