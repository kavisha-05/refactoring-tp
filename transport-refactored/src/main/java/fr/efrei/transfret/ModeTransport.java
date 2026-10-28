package fr.efrei.transfret;

public enum ModeTransport {
    ROUTE(new TarifRoute(), Delais::route, true, true, null),
    RAIL(new TarifRail(), Delais::rail, true, false, null),
    MER(new TarifMer(), Delais::mer, true, true, "pas d'option urgente pour le transport maritime"),
    AIR(new TarifAir(), Delais::air, false, false, null);

private final TarifTransport tarif;
    private final DelaiTransport delai;
    private final boolean marchandisesDangereuses;
    private final boolean refrigeration;
    private final String erreurUrgence;

    ModeTransport(TarifTransport tarif, DelaiTransport delai, boolean marchandisesDangereuses,
                  boolean refrigeration, String erreurUrgence) {
        this.tarif = tarif;
        this.delai = delai;
        this.marchandisesDangereuses = marchandisesDangereuses;
        this.refrigeration = refrigeration;
        this.erreurUrgence = erreurUrgence;
    }

    public TarifTransport tarif() { return tarif; }
    public DelaiTransport delai() { return delai; }
    public boolean accepteDangereux() { return marchandisesDangereuses; }
    public boolean accepteRefrigere() { return refrigeration; }
    public String erreurUrgence() { return erreurUrgence; }
}
