package fachkonzept;

import datenhaltung.*;

public class VeranstaltungManager {
    private IVeranstaltungDAO veranstaltungDAO;
    private IVeranstaltungsortDAO veranstaltungsortDAO;
    private IAdresseDAO adresseDAO;

    public VeranstaltungManager(IVeranstaltungDAO veranstaltungDAO, IVeranstaltungsortDAO veranstaltungsortDAO, IAdresseDAO adresseDAO) {
        this.veranstaltungDAO = veranstaltungDAO;
        this.veranstaltungsortDAO = veranstaltungsortDAO;
        this.adresseDAO = adresseDAO;
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
}
