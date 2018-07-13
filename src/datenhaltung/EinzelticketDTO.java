package datenhaltung;

import oberflaeche.SprachenController;

public class EinzelticketDTO extends TicketDTO {
    private VeranstaltungDTO veranstaltung;
    private Platztyp platztyp;

    public EinzelticketDTO(long ticketnummer) {
        super(ticketnummer);
    }

    public void setVeranstaltung(VeranstaltungDTO veranstaltung) {
        this.veranstaltung = veranstaltung;
    }

    public VeranstaltungDTO getVeranstaltung() {
        return veranstaltung;
    }

    public Platztyp getPlatztyp() {
        return platztyp;
    }

    public void setPlatztyp(Platztyp platztyp) {
        this.platztyp = platztyp;
    }

    @Override
    public String toString() {
        SprachenController sc = SprachenController.getInstance();
        return sc.getText("einzelticket")+" " + (platztyp==Platztyp.STEHPLATZ?sc.getText("stehplatz"):sc.getText("sitzplatz"))+ " " + getPreis() + "    " + veranstaltung;
    }
}
