package fr.efrei.transfret;

import java.util.List;

public class Bateau extends Vehicule implements VehiculeMotorise {
    private static final double KM_PAR_MILLE_NAUTIQUE = 1.852;
    private static final double TEMPERATURE = 4;
    private final boolean conteneursRefrigeres;
    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, capaciteMax);
        this.conteneursRefrigeres = conteneursRefrigeres;
    }
    public boolean accepte(List<Colis> colis, ModeTransport mode) {
        return mode == ModeTransport.MER && (conteneursRefrigeres || colis.stream().noneMatch(c -> c.getType().estRefrigere()));
    }
    public void partir(int distance) {
        faireLePlein();
        System.out.println("Le navire " + immatriculation + " navigue sur " + (int) (distance / KM_PAR_MILLE_NAUTIQUE) + " milles nautiques");
        if (chargement.stream().anyMatch(c -> c.getType().estRefrigere())) {
            System.out.println("Conteneurs frigorifiques réglés à " + TEMPERATURE + "°C");
        }
    }
}
