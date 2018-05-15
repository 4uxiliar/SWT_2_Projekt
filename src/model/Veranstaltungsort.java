package model;

public class Veranstaltungsort {
    private String name;
    private Adresse adresse;

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return name + "&nbsp;&nbsp;&nbsp;&nbsp;" +adresse;
    }
}
