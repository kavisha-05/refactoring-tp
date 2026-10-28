package fr.efrei.transfret;

public class RemisePrix extends DecorateurPrix {
    private final PolitiqueRemise remise;
    public RemisePrix(Prix prix, PolitiqueRemise remise) { super(prix); this.remise = remise; }
    public double calculer() { return remise.appliquer(prix.calculer()); }
}
