package fachkonzept;

import datenhaltung.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketManagerTest {
    private IEinzelticketDAO einzelticketDAO = mock(EinzelticketDAOMySQL.class);
    private IVeranstaltungDAO veranstaltungDAO = mock(VeranstaltungDAOMySQL.class);
    private IVeranstaltungsortDAO veranstaltungsortDAO = mock(VeranstaltungsortDAOMySQL.class);
    private IAdresseDAO adresseDAO = mock(AdresseDAOMySQL.class);
    private ISerienticketDAO serienticketDAO = mock(SerienticketDAOMySQL.class);
    private TicketManager ticketManager = new TicketManager(einzelticketDAO,veranstaltungDAO,veranstaltungsortDAO,adresseDAO,serienticketDAO);

    @Test
    void ladeTickets() {
        VeranstaltungDTO veranstaltungEins = new VeranstaltungDTO(1);
        veranstaltungEins.setPreis(10.0);
        VeranstaltungDTO veranstaltungZwei = new VeranstaltungDTO(2);
        veranstaltungZwei.setPreis(10.0);
        VeranstaltungsortDTO veranstaltungsort = new VeranstaltungsortDTO(1);
        veranstaltungsort.setName("Teststadion");
        AdresseDTO adresse = new AdresseDTO(1);
        adresse.setStrasse("Teststraße");
        adresse.setOrt("Testort");
        adresse.setHausnumer("123a");
        adresse.setPlz("12345");
        veranstaltungsort.setAdresse(adresse);
        veranstaltungEins.setVeranstaltungsort(veranstaltungsort);
        veranstaltungZwei.setVeranstaltungsort(veranstaltungsort);

        LinkedList<EinzelticketDTO> einzeltickets = new LinkedList<>();
        EinzelticketDTO einzelticket =  new EinzelticketDTO(1);
        einzelticket.setVeranstaltung(new VeranstaltungDTO(1));
        einzeltickets.add(einzelticket);
        when(einzelticketDAO.selectAllByBesitzerId(1)).thenReturn(einzeltickets);
        when(veranstaltungDAO.selectById(1)).thenReturn(veranstaltungEins);
        when(veranstaltungDAO.selectById(2)).thenReturn(veranstaltungZwei);
        when(veranstaltungsortDAO.selectById(1)).thenReturn(veranstaltungsort);
        when(adresseDAO.selectById(1)).thenReturn(adresse);

        LinkedList<SerienticketDTO> serientickets = new LinkedList<>();
        SerienticketDTO serienticket = new SerienticketDTO(1);
        serienticket.setPreis(18);
        HashMap<VeranstaltungDTO, Platztyp> veranstaltungen = new HashMap<>();
        veranstaltungen.put(veranstaltungEins, Platztyp.STEHPLATZ);
        veranstaltungen.put(veranstaltungZwei, Platztyp.STEHPLATZ);
        serienticket.setVeranstaltungen(veranstaltungen);
        serientickets.add(serienticket);
        when(serienticketDAO.selectAllByBesitzerId(1)).thenReturn(serientickets);

        AccountDTO account = new AccountDTO(1);
        ticketManager.ladeTickets(account);

        assertEquals(veranstaltungEins, ((EinzelticketDTO) account.getTickets()[0]).getVeranstaltung());
        assertEquals(2, ((SerienticketDTO) account.getTickets()[1]).getVeranstaltungen().size());
    }
}