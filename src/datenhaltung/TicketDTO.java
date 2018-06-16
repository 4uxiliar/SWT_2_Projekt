package datenhaltung;

public abstract class TicketDTO {
    private final long ticketnummer;
    private double preis;

    public TicketDTO(long ticketnummer) {
        this.ticketnummer = ticketnummer;
    }

    public long getTicketnummer() {
        return ticketnummer;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }
}
