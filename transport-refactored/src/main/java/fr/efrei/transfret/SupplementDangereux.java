package fr.efrei.transfret;

public class SupplementDangereux extends DecorateurPrix {
    private static final double COEFFICIENT = 1.30;
    public SupplementDangereux(Prix prix) { super(prix); }
    public double calculer() { return prix.calculer() * COEFFICIENT; }
}
