package fr.efrei.transfret;

/**
 * Racine de composition : le seul endroit qui construit les objets et les relie entre eux.
 */
public class Application {

    private final ClientRepository clients = new ClientRepositoryMemoire();
    private final ExpeditionRepository expeditions = new ExpeditionRepositoryMemoire();
    private final EmailConsole emails = new EmailConsole();
    private final SmsConsole sms = new SmsConsole();
    private final GenerateurFacture generateurFacture = new GenerateurFacture();
    private final ServiceClient serviceClient;
    private final ServiceExpedition serviceExpedition;

    public Application() {
        this(false);
    }

    public Application(boolean horsGabaritActif) {
        Notificateur notificateur = new Notificateur(emails, sms);
        serviceClient = new ServiceClient(clients, new ValidateurClient(), notificateur);
        serviceExpedition = new ServiceExpedition(expeditions, new CalculateurPrix(new AssemblagePrix(horsGabaritActif)),
                new AffectationVehicule(Flotte.standard()), new ValidateurExpedition(),
                new EstimateurDelai(), notificateur, new GenerateurIdExpedition());
    }

    public ServiceClient serviceClient() {
        return serviceClient;
    }

    public ServiceExpedition serviceExpedition() {
        return serviceExpedition;
    }

    public GenerateurFacture generateurFacture() {
        return generateurFacture;
    }

    public ClientRepository clients() {
        return clients;
    }

    public ExpeditionRepository expeditions() {
        return expeditions;
    }

    public EmailConsole emails() {
        return emails;
    }

    public SmsConsole sms() {
        return sms;
    }
}
