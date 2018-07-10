package datenhaltung;

import misc.LoggingController;

import java.sql.SQLException;

public class AdresseDAOMySQL implements IAdresseDAO {
    @Override
    public AdresseDTO selectById(long id) {
        AdresseDTO adresse = new AdresseDTO(-1);
        try {
            ClosedResultSet crs = DatenbankController.getInstance().anfragen("SELECT * FROM ADRESSE WHERE ADRESSE_ID=?", id);
            while (crs.next()) {
                adresse = new AdresseDTO(crs.getLong("ADRESSE_ID"));
                adresse.setOrt(crs.getString("ORT"));
                adresse.setPlz(crs.getString("POSTLEITZAHL"));
                adresse.setStrasse(crs.getString("STRASSE"));
                adresse.setHausnumer(crs.getString("HAUSNUMMER"));
            }
        } catch (SQLException e) {
            LoggingController.getInstance().getLogger().severe(e.getMessage());
        }
        return adresse;
    }
}
