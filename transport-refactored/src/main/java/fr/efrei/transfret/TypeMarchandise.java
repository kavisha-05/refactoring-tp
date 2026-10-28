package fr.efrei.transfret;

import java.util.function.UnaryOperator;

public enum TypeMarchandise {
    STANDARD("Standard", prix -> prix, false, false),
    FRAGILE("Fragile", SupplementFragile::new, false, false),
    DANGEREUX("Dangereux", SupplementDangereux::new, true, false),
    REFRIGERE("Réfrigéré", SupplementRefrigere::new, false, true);

    private final String libelle;
    private final UnaryOperator<Prix> supplement;
    private final boolean dangereux;
    private final boolean refrigere;

    TypeMarchandise(String libelle, UnaryOperator<Prix> supplement, boolean dangereux, boolean refrigere) {
        this.libelle = libelle;
        this.supplement = supplement;
        this.dangereux = dangereux;
        this.refrigere = refrigere;
    }

    public String getLibelle() {
        return libelle;
    }

    public Prix appliquerSupplement(Prix prix) { return supplement.apply(prix); }
    public boolean estDangereux() { return dangereux; }
    public boolean estRefrigere() { return refrigere; }
}
