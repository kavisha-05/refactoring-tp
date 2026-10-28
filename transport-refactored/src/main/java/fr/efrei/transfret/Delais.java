package fr.efrei.transfret;

/** Calculs de délai indépendants pour chaque mode de transport. */
public final class Delais {
    private static final double VITESSE_ROUTE_KMH = 70.0;
    private static final int HEURES_ROUTE = 9;
    private static final int HEURES_ROUTE_URGENT = 18;
    private static final double KM_PAR_JOUR_RAIL = 1440.0;
    private static final double KM_PAR_JOUR_MER = 720.0;

private static final int JOURS_RAIL = 1;
    private static final int REDUCTION_RAIL_URGENT = 1;
    private static final int JOURS_MER = 2;

private static final int DISTANCE_LONGUE_AIR = 3000;
    private static final int JOURS_AIR = 1;
    private static final int JOURS_AIR_LONG = 2;

    private Delais() { }

    public static int route(int distance, boolean urgent) {
        return (int) Math.ceil((distance / VITESSE_ROUTE_KMH) / (urgent ? HEURES_ROUTE_URGENT : HEURES_ROUTE));
    }

    public static int rail(int distance, boolean urgent) {
        return (int) Math.ceil(distance / KM_PAR_JOUR_RAIL) + JOURS_RAIL - (urgent ? REDUCTION_RAIL_URGENT : 0);
    }

    public static int mer(int distance, boolean urgent) {
        return (int) Math.ceil(distance / KM_PAR_JOUR_MER) + JOURS_MER;
    }

public static int air(int distance, boolean urgent) {
        return !urgent && distance > DISTANCE_LONGUE_AIR ? JOURS_AIR_LONG : JOURS_AIR;
    }
}
