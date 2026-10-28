package fr.efrei.transfret;

import java.util.List;

public class AssemblagePrix {
    private static final double LIMITE_GABARIT_CM = 200;
    private final boolean horsGabaritActif;

    public AssemblagePrix(boolean horsGabaritActif) { this.horsGabaritActif = horsGabaritActif; }
    public Prix assembler(Prix base, List<Colis> colis, boolean urgent, boolean assurance, PolitiqueRemise remise) {
        Prix prix = base;
        for (TypeMarchandise type : TypeMarchandise.values()) {
            if (colis.stream().anyMatch(c -> c.getType() == type)) prix = type.appliquerSupplement(prix);
        }
        if (horsGabaritActif && colis.stream().anyMatch(c -> c.getLongueur() > LIMITE_GABARIT_CM || c.getLargeur() > LIMITE_GABARIT_CM || c.getHauteur() > LIMITE_GABARIT_CM)) prix = new SupplementHorsGabarit(prix);
        if (urgent) prix = new SupplementUrgence(prix);
        prix = new RemisePrix(prix, remise);
        if (assurance) {
            double valeur = 0;
            for (Colis c : colis) valeur += c.getValeurDeclaree();
            prix = new Assurance(prix, valeur);
        }
        return prix;
    }
}
