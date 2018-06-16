package fachkonzept;

import datenhaltung.*;
import misc.InvalidDataException;

import java.util.LinkedList;

public class Fachkonzept {
    private AccountDTO activeAccount;
    private IAccountDAO accountDAO;
    private IEinzelticketDAO einzelticketDAO;
    private ISerienticketDAO serienticketDAO;
    private IVeranstaltungDAO veranstaltungDAO;
    private IVeranstaltungsortDAO veranstaltungsortDAO;
    private IAdresseDAO adresseDAO;

    public Fachkonzept(IAccountDAO accountDAO, IEinzelticketDAO einzelticketDAO, ISerienticketDAO serienticketDAO, IVeranstaltungDAO veranstaltungDAO, IVeranstaltungsortDAO veranstaltungsortDAO, IAdresseDAO adresseDAO) {
        this.accountDAO = accountDAO;
        this.einzelticketDAO = einzelticketDAO;
        this.serienticketDAO = serienticketDAO;
        this.veranstaltungDAO = veranstaltungDAO;
        this.veranstaltungsortDAO = veranstaltungsortDAO;
        this.adresseDAO = adresseDAO;
    }

    public AccountDTO login(String email, String password) throws InvalidDataException {
        activeAccount = accountDAO.selectByUsernameAndPassword(email, password);
        if (activeAccount != null) {
            ladeTickets(activeAccount);
            return activeAccount;
        }
        throw new InvalidDataException("Falscher Benutzername oder Kennwort.");
    }

    public void registrieren(String email, String password) throws InvalidDataException {
        AccountDTO account = new AccountDTO(-1);
        account.setEmail(email);
        accountDAO.insert(account, password);
    }

    public void ladeTickets(AccountDTO account) {
        LinkedList<TicketDTO> tickets = new LinkedList<>();
        LinkedList<EinzelticketDTO> einzeltickets = einzelticketDAO.selectAllByBesitzerId(account.getId());
        einzeltickets.forEach(einzelticket -> {
            VeranstaltungDTO veranstaltung = veranstaltungDAO.selectById(einzelticket.getVeranstaltung().getVeranstaltung_id());
            VeranstaltungsortDTO veranstaltungsort = veranstaltungsortDAO.selectById(veranstaltung.getVeranstaltungsort().getId());
            AdresseDTO adresse = adresseDAO.selectById(veranstaltungsort.getAdresse().getId());
            veranstaltungsort.setAdresse(adresse);
            veranstaltung.setVeranstaltungsort(veranstaltungsort);
            einzelticket.setVeranstaltung(veranstaltung);
        });
        tickets.addAll(einzeltickets);
        LinkedList<SerienticketDTO> serientickets = serienticketDAO.selectAllByBesitzerId(account.getId());
        for (SerienticketDTO serienticket : serientickets)
            for (VeranstaltungDTO veranstaltung : serienticket.getVeranstaltungen().keySet()) {
                VeranstaltungDTO veranstaltung_neu = veranstaltungDAO.selectById(veranstaltung.getVeranstaltung_id());
                VeranstaltungsortDTO veranstaltungsort = veranstaltungsortDAO.selectById(veranstaltung_neu.getVeranstaltungsort().getId());
                AdresseDTO adresse = adresseDAO.selectById(veranstaltungsort.getAdresse().getId());
                veranstaltungsort.setAdresse(adresse);
                veranstaltung.setEventname(veranstaltung_neu.getEventname());
                veranstaltung.setPreis(veranstaltung_neu.getPreis());
                veranstaltung.setVonDatum(veranstaltung_neu.getVonDatum());
                veranstaltung.setBisDatum(veranstaltung_neu.getBisDatum());
                veranstaltung.setVeranstaltungsort(veranstaltungsort);
            }
        tickets.addAll(serientickets);
        account.setTickets(tickets.toArray(new TicketDTO[serientickets.size()]));
    }

    public VeranstaltungDTO[] ladeVeranstaltungen() {
        VeranstaltungDTO[] veranstaltungen = veranstaltungDAO.selectAll();
        for (VeranstaltungDTO veranstaltung : veranstaltungen) {
            VeranstaltungsortDTO veranstaltungsort = veranstaltungsortDAO.selectById(veranstaltung.getVeranstaltungsort().getId());
            AdresseDTO adresse = adresseDAO.selectById(veranstaltungsort.getAdresse().getId());
            veranstaltungsort.setAdresse(adresse);
            veranstaltung.setVeranstaltungsort(veranstaltungsort);
        }
        return veranstaltungen;
    }

    public void kaufeEinzelticket(VeranstaltungDTO veranstaltung) {
        EinzelticketDTO einzelticket = new EinzelticketDTO(-1);
        einzelticket.setPreis(veranstaltung.getPreis());
        einzelticket.setVeranstaltung(veranstaltung);
        einzelticket.setPlatztyp(Platztyp.SITZPLATZ);
        einzelticketDAO.insert(einzelticket, activeAccount.getId());
    }

    public void kaufeSerienticket(VeranstaltungDTO[] veranstaltungen) {
        SerienticketDTO serienticket = new SerienticketDTO(-1);
        double preis = 0.0;
        for (VeranstaltungDTO veranstaltung : veranstaltungen) {
            serienticket.getVeranstaltungen().put(veranstaltung, Platztyp.SITZPLATZ);
            preis += veranstaltung.getPreis();
        }
        serienticket.setPreis(preis * 0.9);
        serienticketDAO.insert(serienticket, activeAccount.getId());
    }
}

