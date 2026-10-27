package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Flotte {

    private final List<Vehicule> vehicules;

    public Flotte(List<Vehicule> vehicules) {
        this.vehicules = new ArrayList<>(vehicules);
    }

    /** La flotte de TransFret, neuve : tous les véhicules sont disponibles. */
    public static Flotte standard() {
        return new Flotte(List.of(
                new Camion("AB-123-CD", 19000, false),
                new Camion("EF-456-GH", 12000, true),
                new CamionFrigorifique("IJ-789-KL", 8000),
                new Train("TR-0001", 600000),
                new Bateau("CMA-CGM-01", 20000000, true),
                new Avion("F-GTXA", 100000)));
    }

    public List<Vehicule> getVehicules() {
        return Collections.unmodifiableList(vehicules);
    }
}
