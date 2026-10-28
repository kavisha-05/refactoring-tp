package fr.efrei.transfret;

public class RemiseEntreprise implements PolitiqueRemise {
    private static final double SEUIL = 5000;
    private static final double COEFFICIENT_NORMAL = 0.90;
    private static final double COEFFICIENT_GROS_MONTANT = 0.85;

    public double appliquer(double prix) {
        return prix * (prix > SEUIL ? COEFFICIENT_GROS_MONTANT : COEFFICIENT_NORMAL);
    }
}
