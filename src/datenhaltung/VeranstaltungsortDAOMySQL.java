package datenhaltung;

import misc.LoggingController;

import java.sql.SQLException;

public class VeranstaltungsortDAOMySQL implements IVeranstaltungsortDAO {
    @Override
    public VeranstaltungsortDTO selectById(long id) {
        VeranstaltungsortDTO veranstaltungsort = new VeranstaltungsortDTO(-1);
        try {
            ClosedResultSet crs = DatenbankController.getInstance().anfragen("SELECT * FROM VERANSTALTUNGSORT WHERE VERANSTALTUNGSORT_ID=?", id);
            while(crs.next()) {
                veranstaltungsort = new VeranstaltungsortDTO(crs.getLong("VERANSTALTUNGSORT_ID"));
                veranstaltungsort.setName(crs.getString("VERANSTALTUNGSORT_NAME"));
                veranstaltungsort.setAdresse(new AdresseDTO(crs.getLong("ADRESSE")));
            }
        } catch (SQLException e) {
            LoggingController.getInstance().getLogger().severe(e.getMessage());
        }
        return  veranstaltungsort;
    }
}
