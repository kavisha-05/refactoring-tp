package fr.efrei.transfret;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateursTest {
    private final Prix base = () -> 100;

    @Test void chaqueSupplement() {
        assertEquals(115, new SupplementFragile(base).calculer(), 0.00001);
        assertEquals(130, new SupplementDangereux(base).calculer(), 0.00001);
        assertEquals(120, new SupplementRefrigere(base).calculer(), 0.00001);
        assertEquals(150, new SupplementUrgence(base).calculer(), 0.00001);
    }

    @Test void assuranceEtRemise() {
        assertEquals(115, new Assurance(base, 100).calculer(), 0.00001);
        assertEquals(120, new Assurance(base, 1000).calculer(), 0.00001);
        assertEquals(80, new RemisePrix(base, new RemisePremium()).calculer(), 0.00001);
        Prix prix = new Assurance(new RemisePrix(new SupplementUrgence(() -> 4000), new RemiseEntreprise()), 1000);
        assertEquals(5120, prix.calculer(), 0.00001);
    }
}
