package fr.efrei.transfret;

import java.util.Optional;

public interface ExpeditionRepository {

    /** Enregistre une nouvelle expédition ou met à jour une expédition existante. */
    void enregistrer(Expedition expedition);

    Optional<Expedition> trouver(String id);
}
