package fachkonzept;

import datenhaltung.*;
import misc.InvalidDataException;

public class Fassade {
    private static Fassade fassade;

    private IAccountDAO accountDAO;
    private IEinzelticketDAO einzelticketDAO;
    private ISerienticketDAO serienticketDAO;
    private IVeranstaltungDAO veranstaltungDAO;
    private IVeranstaltungsortDAO veranstaltungsortDAO;
    private IAdresseDAO adresseDAO;

    private AccountManager accountManager;
    private TicketManager ticketManager;
    private VeranstaltungManager veranstaltungManager;

    private Fassade() {
        this.accountDAO = new AccountDAOMySQL();
        this.einzelticketDAO = new EinzelticketDAOMySQL();
        this.serienticketDAO = new SerienticketDAOMySQL();
        this.veranstaltungDAO = new VeranstaltungDAOMySQL();
        this.veranstaltungsortDAO = new VeranstaltungsortDAOMySQL();
        this.adresseDAO = new AdresseDAOMySQL();

        accountManager = new AccountManager(accountDAO);
        ticketManager = new TicketManager(einzelticketDAO, veranstaltungDAO, veranstaltungsortDAO, adresseDAO, serienticketDAO);
        veranstaltungManager = new VeranstaltungManager(veranstaltungDAO, veranstaltungsortDAO, adresseDAO);
    }

    public static Fassade getInstance() {
        if (fassade == null)
            fassade = new Fassade();
        return fassade;
    }

    public AccountDTO getActiveAccount() {
        return accountManager.getActiveAccount();
    }

    public AccountDTO login(String email, String password) throws InvalidDataException {
        return accountManager.login(email, password);
    }

    public void registrieren(String email, String password) throws InvalidDataException {
        accountManager.registrieren(email, password);
    }


    public void ladeTickets(AccountDTO account) {
        ticketManager.ladeTickets(account);
    }

    public VeranstaltungDTO[] ladeVeranstaltungen() {
        return veranstaltungManager.ladeVeranstaltungen();
    }

    public void kaufeEinzelticket(VeranstaltungDTO veranstaltung) {
        ticketManager.kaufeEinzelticket(veranstaltung);
    }

    public void kaufeSerienticket(VeranstaltungDTO[] veranstaltungen) {
        ticketManager.kaufeSerienticket(veranstaltungen);
    }


}

