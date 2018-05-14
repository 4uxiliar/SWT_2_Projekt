package model;

import java.util.HashMap;

public class Serienticket extends Ticket {
    private HashMap<Veranstaltung, Platztyp> veranstaltungen;

    public HashMap<Veranstaltung, Platztyp> getVeranstaltungen() {
        return veranstaltungen;
    }

    public void setVeranstaltungen(HashMap<Veranstaltung, Platztyp> veranstaltungen) {
        this.veranstaltungen = veranstaltungen;
    }
}
