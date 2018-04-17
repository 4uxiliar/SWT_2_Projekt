package model;

public class Adresse {
    private String wohnort;
    private String plz;
    private String strasse;
    private String hausnumer;

    public Adresse(String wohnort, String plz, String strasse, String hausnumer) {
        this.wohnort = wohnort;
        this.plz = plz;
        this.strasse = strasse;
        this.hausnumer = hausnumer;
    }
}
