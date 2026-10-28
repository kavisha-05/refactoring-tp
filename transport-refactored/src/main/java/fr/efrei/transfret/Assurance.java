package fr.efrei.transfret;

public class Assurance extends DecorateurPrix {
    private static final double TAUX = 0.02;
    private static final double MINIMUM = 15;
    private final double valeur;
    public Assurance(Prix prix, double valeur) { super(prix); this.valeur = valeur; }
    public double calculer() { return prix.calculer() + Math.max(MINIMUM, valeur * TAUX); }
}
