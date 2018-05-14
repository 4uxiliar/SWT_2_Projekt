package model;

import java.util.ArrayList;
import java.util.Date;

public class Account {
    private final long id;
    private String email;
    private String passwort;
    private Date geburtsdatum;
    private ArrayList<Ticket> tickets;


    public Account(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }


    public ArrayList<Ticket> getTickets() {
        return tickets;
    }


}
