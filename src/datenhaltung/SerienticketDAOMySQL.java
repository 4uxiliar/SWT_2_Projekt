package datenhaltung;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class SerienticketDAOMySQL implements ISerienticketDAO {
    @Override
    public LinkedList<SerienticketDTO> selectAllByBesitzerId(long id) {
        LinkedList<SerienticketDTO> tickets = new LinkedList<>();
        try {
            DatenbankController dbc = DatenbankController.getInstance();
            ClosedResultSet crs = dbc.executeQuery("SELECT * FROM SERIENTICKET WHERE BESITZER=?", id);
            while (crs.next()) {
                SerienticketDTO serienticket = new SerienticketDTO(crs.getLong("SERIENTICKET_ID"));
                serienticket.setPreis(crs.getDouble("PREIS"));

                HashMap<VeranstaltungDTO, Platztyp> veranstaltungenPlatztyp = new HashMap<>();
                ClosedResultSet subcrs = dbc.executeQuery("SELECT VERANSTALTUNG, PLATZTYP FROM SERIENTICKET_VERANSTALTUNG WHERE SERIENTICKET =?",
                        serienticket.getTicketnummer());
                while (subcrs.next()) {
                    Platztyp platztyp = null;
                    switch (subcrs.getString("PLATZTYP")) {
                        case "STEHPLATZ":
                            platztyp = Platztyp.STEHPLATZ;
                            break;
                        case "SITZPLATZ":
                            platztyp = Platztyp.SITZPLATZ;
                            break;
                        default:
                            System.out.println(crs.getString("STEHPLATZ"));
                    }
                    veranstaltungenPlatztyp.put(new VeranstaltungDTO(subcrs.getLong("VERANSTALTUNG")), platztyp);
                }
                serienticket.setVeranstaltungen(veranstaltungenPlatztyp);
                tickets.add(serienticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public boolean insert(SerienticketDTO serienticketDTO, long accountId) {
        try {
            DatenbankController.getInstance().execute("INSERT INTO SERIENTICKET VALUES (NULL,?,?)", serienticketDTO.getPreis(), accountId);
            ClosedResultSet crs = DatenbankController.getInstance().executeQuery("SELECT max(SERIENTICKET_ID) FROM SERIENTICKET");
            crs.next();
            final long serienticketId = crs.getLong("max(SERIENTICKET_ID)");
            serienticketDTO.getVeranstaltungen().forEach((veranstaltungDTO, platztyp) -> {
                DatenbankController.getInstance().execute(
                        "INSERT INTO SERIENTICKET_VERANSTALTUNG VALUES (?,?,?)", serienticketId, veranstaltungDTO.getVeranstaltung_id(), platztyp.name());

            });
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
