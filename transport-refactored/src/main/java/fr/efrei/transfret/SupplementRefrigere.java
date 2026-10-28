package fr.efrei.transfret;

public class SupplementRefrigere extends DecorateurPrix {
    private static final double COEFFICIENT = 1.20;
    public SupplementRefrigere(Prix prix) { super(prix); }
    public double calculer() { return prix.calculer() * COEFFICIENT; }
}
