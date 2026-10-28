package fr.efrei.transfret;

public class TarifAir implements TarifTransport {
    private static final double PRIX_KM = 0.6;
    private static final double PRIX_KG = 4.5;
    private static final double KG_PAR_M3 = 167;

    public double calculer(int distance, double poids, double volume) {
        return distance * PRIX_KM + Math.max(poids, volume * KG_PAR_M3) * PRIX_KG;
    }
}
