package fachkonzept;

import datenhaltung.*;

import java.util.LinkedList;

public class TicketManager {
    private IEinzelticketDAO einzelticketDAO;
    private IVeranstaltungDAO veranstaltungDAO;
    private IVeranstaltungsortDAO veranstaltungsortDAO;
    private IAdresseDAO adresseDAO;
    private ISerienticketDAO serienticketDAO;

    public TicketManager(IEinzelticketDAO einzelticketDAO, IVeranstaltungDAO veranstaltungDAO, IVeranstaltungsortDAO veranstaltungsortDAO, IAdresseDAO adresseDAO, ISerienticketDAO serienticketDAO) {
        this.einzelticketDAO = einzelticketDAO;
        this.veranstaltungDAO = veranstaltungDAO;
        this.veranstaltungsortDAO = veranstaltungsortDAO;
        this.adresseDAO = adresseDAO;
        this.serienticketDAO = serienticketDAO;
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

    public void kaufeEinzelticket(VeranstaltungDTO veranstaltung) {
        EinzelticketDTO einzelticket = new EinzelticketDTO(-1);
        einzelticket.setPreis(veranstaltung.getPreis());
        einzelticket.setVeranstaltung(veranstaltung);
        einzelticket.setPlatztyp(Platztyp.SITZPLATZ);
        einzelticketDAO.insert(einzelticket, Fassade.getInstance().getActiveAccount().getId());
    }

    public void kaufeSerienticket(VeranstaltungDTO[] veranstaltungen) {
        SerienticketDTO serienticket = new SerienticketDTO(-1);
        double preis = 0.0;
        for (VeranstaltungDTO veranstaltung : veranstaltungen) {
            serienticket.getVeranstaltungen().put(veranstaltung, Platztyp.SITZPLATZ);
            preis += veranstaltung.getPreis();
        }
        serienticket.setPreis(preis * 0.9);
        serienticketDAO.insert(serienticket, Fassade.getInstance().getActiveAccount().getId());
    }
}
