package fr.efrei.transfret;

import java.util.List;

public class AssemblagePrix {
    public Prix assembler(Prix base, List<Colis> colis, boolean urgent, boolean assurance, PolitiqueRemise remise) {
        Prix prix = base;
        for (TypeMarchandise type : TypeMarchandise.values()) {
            if (colis.stream().anyMatch(c -> c.getType() == type)) prix = type.appliquerSupplement(prix);
        }
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
