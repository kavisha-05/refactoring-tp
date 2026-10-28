package fr.efrei.transfret;

public enum TypeClient {
    PARTICULIER(new RemiseParticulier(), false, false),
    ENTREPRISE(new RemiseEntreprise(), true, false),
    PREMIUM(new RemisePremium(), false, true);

    private final PolitiqueRemise remise;
    private final boolean siretRequis;
    private final boolean notificationSms;

    TypeClient(PolitiqueRemise remise, boolean siretRequis, boolean notificationSms) {
        this.remise = remise;
        this.siretRequis = siretRequis;
        this.notificationSms = notificationSms;
    }

    public PolitiqueRemise remise() { return remise; }
    public boolean siretRequis() { return siretRequis; }
    public boolean notificationSms() { return notificationSms; }
}
