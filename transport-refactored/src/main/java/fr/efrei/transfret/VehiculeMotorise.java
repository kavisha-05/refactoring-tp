package fr.efrei.transfret;

/** Capacité réservée aux véhicules qui utilisent du carburant. */
public interface VehiculeMotorise extends IVehicule {
    default void faireLePlein() {
        System.out.println("Plein effectué pour " + getImmatriculation());
    }
}
