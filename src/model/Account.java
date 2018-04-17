package model;

import java.util.Date;

public class Account {
    private String email;
    private String passwort;
    private Date geburtsdamtum;
    private Adresse adresse;

    public Account(String email, String passwort, Date geburtsdamtum, Adresse adresse) {
        this.email = email;
        this.passwort = passwort;
        this.geburtsdamtum = geburtsdamtum;
        this.adresse = adresse;
    }
}
