package fr.efrei.transfret;

public class SupplementFragile extends DecorateurPrix {
    private static final double COEFFICIENT = 1.15;
    public SupplementFragile(Prix prix) { super(prix); }
    public double calculer() { return prix.calculer() * COEFFICIENT; }
}
