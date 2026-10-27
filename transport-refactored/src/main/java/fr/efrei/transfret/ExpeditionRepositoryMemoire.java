package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExpeditionRepositoryMemoire implements ExpeditionRepository {

    private final Map<String, Expedition> expeditions = new HashMap<>();

    @Override
    public void enregistrer(Expedition expedition) {
        expeditions.put(expedition.getId(), expedition);
    }

    @Override
    public Optional<Expedition> trouver(String id) {
        return Optional.ofNullable(expeditions.get(id));
    }
}
