package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {
    private final AssemblagePrix assemblage;

    public CalculateurPrix(AssemblagePrix assemblage) {
        this.assemblage = assemblage;
    }

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent,
                                 boolean assurance, TypeClient typeClient) {
        double poids = 0;
        double volume = 0;
        for (Colis c : colis) {
            poids += c.getPoids();
            volume += c.getVolume();
        }
        double base = mode.tarif().calculer(distance, poids, volume);
        Prix prix = assemblage.assembler(() -> base, colis, urgent, assurance, typeClient.remise());
        return Utils.arrondir(prix.calculer());
    }
}
