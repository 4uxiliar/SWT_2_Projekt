package model;

import java.util.Date;
import java.util.List;

public class Veranstaltung {
    private String eventname;
    private Date vonDatum;
    private Date bisDatum;
    private int verfuegbareSitzplaetze;
    private int verfuegbareStehplaetze;
    private Ticket[] sitzplaetze;
    private Ticket[] stehplaetze;
    private Veranstaltungsort veranstaltungsort;

    public Veranstaltung(String eventname, Date vonDatum, Date bisDatum, int anzahlSitzplaetze, int preisSitzplaetze, int anzahlStehplaetze, int preisStehplaetze, Veranstaltungsort veranstaltungsort) {
        this.eventname = eventname;
        this.vonDatum = vonDatum;
        this.bisDatum = bisDatum;
        sitzplaetze = new Ticket[anzahlSitzplaetze];
        for(int i=0; i < anzahlSitzplaetze; i++)
            sitzplaetze[i]=new Ticket(preisSitzplaetze, Platztyp.SITZPLATZ);
        this.verfuegbareSitzplaetze = anzahlSitzplaetze;
        stehplaetze = new Ticket[anzahlStehplaetze];
        for(int i = 0; i < anzahlStehplaetze; i++)
            stehplaetze[i]= new Ticket(preisStehplaetze, Platztyp.STEHPLATZ);
        this.verfuegbareStehplaetze = anzahlStehplaetze;
        this.veranstaltungsort = veranstaltungsort;
    }
}
