package model;

public class Einzelticket extends Ticket {

    private Platztyp platztyp;
    private Veranstaltung veranstaltung;

    public Platztyp getPlatztyp() {
        return platztyp;
    }

    public void setPlatztyp(Platztyp platztyp) {
        this.platztyp = platztyp;
    }

    public void setVeranstaltung(Veranstaltung veranstaltung) {
        this.veranstaltung = veranstaltung;
    }

    @Override
    public String toString() {
        return "Einzelticket    " + getPreis() + "    " + platztyp + "    " + veranstaltung;
    }
}
