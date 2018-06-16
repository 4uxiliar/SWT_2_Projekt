package datenhaltung;

import java.util.Date;

public class VeranstaltungDTO {
    private final long veranstaltung_id;
    private String eventname;
    private Double preis;
    private Date vonDatum;
    private Date bisDatum;
    private VeranstaltungsortDTO veranstaltungsort;

    public VeranstaltungDTO(long veranstaltung_id) {
        this.veranstaltung_id = veranstaltung_id;
    }

    public long getVeranstaltung_id() {
        return veranstaltung_id;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public Date getVonDatum() {
        return vonDatum;
    }

    public void setVonDatum(Date vonDatum) {
        this.vonDatum = vonDatum;
    }

    public Date getBisDatum() {
        return bisDatum;
    }

    public void setBisDatum(Date bisDatum) {
        this.bisDatum = bisDatum;
    }

    public void setVeranstaltungsort(VeranstaltungsortDTO veranstaltungsort) {
        this.veranstaltungsort = veranstaltungsort;
    }

    public VeranstaltungsortDTO getVeranstaltungsort() {
        return veranstaltungsort;
    }

    @Override
    public String toString() {
        return eventname + "    " + preis + "    " + vonDatum + "    " + bisDatum + "    " + veranstaltungsort;
    }
}
