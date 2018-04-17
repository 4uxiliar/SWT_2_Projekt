package model;

public class Serienticket extends Ticket {
    private Veranstaltung[] veranstaltungen;

    public Serienticket(double preis, Platztyp platztyp, Veranstaltung[] veranstaltungen) {
        super(preis, platztyp);
        this.veranstaltungen = veranstaltungen;
    }
}
