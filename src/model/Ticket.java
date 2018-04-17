package model;

public class Ticket {
    private long ticketnummer;
    private double preis;
    private Platztyp platztyp;

    public Ticket(double preis, Platztyp platztyp) {
        this.preis = preis;
        this.platztyp = platztyp;
    }
}
