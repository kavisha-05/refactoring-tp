package fr.efrei.transfret;

public class SupplementUrgence extends DecorateurPrix {
    private static final double COEFFICIENT = 1.5;
    public SupplementUrgence(Prix prix) { super(prix); }
    public double calculer() { return prix.calculer() * COEFFICIENT; }
}
