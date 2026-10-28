package fr.efrei.transfret;

public class RemisePremium implements PolitiqueRemise {
    private static final double COEFFICIENT = 0.80;

    public double appliquer(double prix) { return prix * COEFFICIENT; }
}
