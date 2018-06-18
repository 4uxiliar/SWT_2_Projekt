package fachkonzept;

import datenhaltung.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VeranstaltungManagerTest {
    private IVeranstaltungDAO veranstaltungDAO = mock(VeranstaltungDAOMySQL.class);
    private IVeranstaltungsortDAO veranstaltungsortDAO = mock(VeranstaltungsortDAOMySQL.class);
    private IAdresseDAO adresseDAO = mock(AdresseDAOMySQL.class);

    private VeranstaltungManager veranstaltungManager = new VeranstaltungManager(veranstaltungDAO, veranstaltungsortDAO, adresseDAO);

    @Test
    public void testLadeVeranstaltungen() {
        VeranstaltungsortDTO veranstaltungsort = new VeranstaltungsortDTO(1);
        veranstaltungsort.setName("Teststadion");
        AdresseDTO adresse = new AdresseDTO(1);
        adresse.setStrasse("Teststraße");
        adresse.setOrt("Testort");
        adresse.setHausnumer("123a");
        adresse.setPlz("12345");
        veranstaltungsort.setAdresse(adresse);

        VeranstaltungDTO[] veranstaltung = {new VeranstaltungDTO(1)};
        veranstaltung[0].setVeranstaltungsort(new VeranstaltungsortDTO(1));

        when(veranstaltungDAO.selectAll()).thenReturn(veranstaltung);
        when(veranstaltungsortDAO.selectById(1)).thenReturn(veranstaltungsort);
        when(adresseDAO.selectById(1)).thenReturn(adresse);

        VeranstaltungDTO[] veranstaltungen = veranstaltungManager.ladeVeranstaltungen();
        assertEquals(veranstaltungsort, veranstaltungen[0].getVeranstaltungsort());
        assertEquals(adresse, veranstaltungen[0].getVeranstaltungsort().getAdresse());
    }

}