package datenhaltung;

import misc.LoggingController;

import java.sql.SQLException;
import java.util.LinkedList;

public class VeranstaltungDAOMySQL implements IVeranstaltungDAO {
    @Override
    public VeranstaltungDTO selectById(long id) {
        VeranstaltungDTO veranstaltung = new VeranstaltungDTO(-1);
        try {
            ClosedResultSet crs = DatenbankController.getInstance().anfragen("SELECT * FROM VERANSTALTUNG WHERE VERANSTALTUNG_ID=?", id);
            while(crs.next())
            {
                veranstaltung = new VeranstaltungDTO(crs.getLong("VERANSTALTUNG_ID"));
                veranstaltung.setEventname(crs.getString("EVENTNAME"));
                veranstaltung.setPreis(crs.getDouble("PREIS"));
                veranstaltung.setVonDatum(crs.getTimestamp("VON_DATUM"));
                veranstaltung.setBisDatum(crs.getTimestamp("BIS_DATUM"));
                veranstaltung.setVeranstaltungsort(new VeranstaltungsortDTO(crs.getLong("VERANSTALTUNGSORT")));
            }
        } catch (SQLException e) {
            LoggingController.getInstance().getLogger().severe(e.getMessage());
        }
        return veranstaltung;
    }

    @Override
    public VeranstaltungDTO[] selectAll() {
        LinkedList<VeranstaltungDTO> veranstaltungen = new LinkedList<>();
        try {
            ClosedResultSet crs = DatenbankController.getInstance().anfragen("SELECT * FROM VERANSTALTUNG");
            while(crs.next())
            {
                VeranstaltungDTO veranstaltung = new VeranstaltungDTO(crs.getLong("VERANSTALTUNG_ID"));
                veranstaltung.setEventname(crs.getString("EVENTNAME"));
                veranstaltung.setPreis(crs.getDouble("PREIS"));
                veranstaltung.setVonDatum(crs.getTimestamp("VON_DATUM"));
                veranstaltung.setBisDatum(crs.getTimestamp("BIS_DATUM"));
                veranstaltung.setVeranstaltungsort(new VeranstaltungsortDTO(crs.getLong("VERANSTALTUNGSORT")));
                veranstaltungen.add(veranstaltung);
            }
        } catch (SQLException e) {
            LoggingController.getInstance().getLogger().severe(e.getMessage());
        }
        return veranstaltungen.toArray(new VeranstaltungDTO[veranstaltungen.size()]);
    }
}
