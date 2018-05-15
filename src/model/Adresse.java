package model;

public class Adresse {
    private String ort;
    private String plz;
    private String strasse;
    private String hausnumer;

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setHausnumer(String hausnumer) {
        this.hausnumer = hausnumer;
    }

    @Override
    public String toString() {
        return ort + "    " + plz + "    " + strasse + "    " + hausnumer;
    }
}
