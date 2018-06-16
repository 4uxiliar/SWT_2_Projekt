package datenhaltung;

public class VeranstaltungsortDTO {
    private final long id;
    private String name;
    private AdresseDTO adresse;

    public VeranstaltungsortDTO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
    }

    public AdresseDTO getAdresse() {
        return adresse;
    }

    @Override
    public String toString() {
        return name + " " +adresse;
    }
}
