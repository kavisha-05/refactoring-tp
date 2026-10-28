package fr.efrei.transfret;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final double CENTIMES_PAR_EURO = 100.0;
    private static final int DISTANCE_PARIS_LYON = 465;
    private static final int DISTANCE_PARIS_MARSEILLE = 775;
    private static final int DISTANCE_PARIS_LILLE = 225;
    private static final int DISTANCE_PARIS_BORDEAUX = 585;
    private static final int DISTANCE_LYON_MARSEILLE = 315;
    private static final int DISTANCE_LE_HAVRE_NEW_YORK = 5850;
    private static final int DISTANCE_MARSEILLE_ALGER = 760;
    private static final int DISTANCE_PARIS_NEW_YORK = 5840;
    private static final int DISTANCE_PARIS_LE_HAVRE = 365;

    public static double arrondir(double valeur) {
        return Math.round(valeur * CENTIMES_PAR_EURO) / CENTIMES_PAR_EURO;
    }

    public static String formaterDate(Date d) {
        if (d == null) {
            return "";
        }
        return FORMAT_DATE.format(d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static String formaterPrix(double prix) {
        return String.format("%.2f €", prix);
    }

    public static boolean emailValide(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // retourne la distance en km entre deux villes, -1 si inconnue
    public static int getDistance(String villeA, String villeB) {
        String a = villeA.toUpperCase();
        String b = villeB.toUpperCase();
        if ((a.equals("PARIS") && b.equals("LE HAVRE")) || (a.equals("LE HAVRE") && b.equals("PARIS"))) {
            return DISTANCE_PARIS_LE_HAVRE;
        } else if ((a.equals("PARIS") && b.equals("LYON")) || (a.equals("LYON") && b.equals("PARIS"))) {
            return DISTANCE_PARIS_LYON;
        } else if ((a.equals("PARIS") && b.equals("MARSEILLE")) || (a.equals("MARSEILLE") && b.equals("PARIS"))) {
            return DISTANCE_PARIS_MARSEILLE;
        } else if ((a.equals("PARIS") && b.equals("LILLE")) || (a.equals("LILLE") && b.equals("PARIS"))) {
            return DISTANCE_PARIS_LILLE;
        } else if ((a.equals("PARIS") && b.equals("BORDEAUX")) || (a.equals("BORDEAUX") && b.equals("PARIS"))) {
            return DISTANCE_PARIS_BORDEAUX;
        } else if ((a.equals("LYON") && b.equals("MARSEILLE")) || (a.equals("MARSEILLE") && b.equals("LYON"))) {
            return DISTANCE_LYON_MARSEILLE;
        } else if ((a.equals("LE HAVRE") && b.equals("NEW YORK")) || (a.equals("NEW YORK") && b.equals("LE HAVRE"))) {
            return DISTANCE_LE_HAVRE_NEW_YORK;
        } else if ((a.equals("MARSEILLE") && b.equals("ALGER")) || (a.equals("ALGER") && b.equals("MARSEILLE"))) {
            return DISTANCE_MARSEILLE_ALGER;
        } else if ((a.equals("PARIS") && b.equals("NEW YORK")) || (a.equals("NEW YORK") && b.equals("PARIS"))) {
            return DISTANCE_PARIS_NEW_YORK;
        }
        return -1;
    }
}
