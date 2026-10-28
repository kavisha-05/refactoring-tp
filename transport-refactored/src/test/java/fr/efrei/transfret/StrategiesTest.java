package fr.efrei.transfret;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategiesTest {
    @Test void tarifs() {
        assertEquals  (130, new TarifRoute().calculer(100, 100, 0), 0.00001);
        assertEquals(150, new TarifRail().calculer(100, 100, 0), 0.00001);
        assertEquals(800.7, new TarifRail().calculer(1000, 10, 0), 0.00001);
        assertEquals(500, new TarifMer().calculer(100, 100, 0), 0.00001);
        assertEquals(1000.3, new TarifMer().calculer(2000, 10, 0), 0.00001);
        assertEquals(510, new TarifAir().calculer(100, 100, 0), 0.00001);
        assertEquals(811.5, new TarifAir().calculer(100, 100, 1), 0.00001);
    }

    @Test void remises() {
        assertEquals(100, new RemiseParticulier().appliquer(100), 0.00001);
        assertEquals(4500, new RemiseEntreprise().appliquer(5000), 0.00001);
        assertEquals(5100, new RemiseEntreprise().appliquer(6000), 0.00001);
        assertEquals(80, new RemisePremium().appliquer(100), 0.00001);
    }
}
