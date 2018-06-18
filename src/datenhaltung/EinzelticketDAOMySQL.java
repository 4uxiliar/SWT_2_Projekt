package datenhaltung;

import java.sql.SQLException;
import java.util.LinkedList;

public class EinzelticketDAOMySQL implements IEinzelticketDAO{
    @Override
    public LinkedList<EinzelticketDTO> selectAllByBesitzerId(long id) {
        LinkedList<EinzelticketDTO> tickets = new LinkedList<>();
        try {
            ClosedResultSet crs = DatenbankController.getInstance().executeQuery("SELECT * FROM EINZELTICKET WHERE BESITZER=?", id);
            while (crs.next()) {
                EinzelticketDTO einzelticket = new EinzelticketDTO(crs.getLong("EINZELTICKET_ID"));
                switch (crs.getString("PLATZTYP")) {
                    case "STEHPLATZ":
                        einzelticket.setPlatztyp(Platztyp.STEHPLATZ);
                    case "SITZPLATZ":
                        einzelticket.setPlatztyp(Platztyp.SITZPLATZ);
                }
                einzelticket.setPreis(crs.getDouble("PREIS"));
                einzelticket.setVeranstaltung(new VeranstaltungDTO(crs.getLong("VERANSTALTUNG")));
                tickets.add(einzelticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public boolean insert(EinzelticketDTO einzelticket, long accountId) {
        try {
            return DatenbankController.getInstance().execute("INSERT INTO EINZELTICKET VALUES (NULL,?,?,?,?)", einzelticket.getPreis(), einzelticket.getPlatztyp().name(), einzelticket.getVeranstaltung().getVeranstaltung_id(), accountId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
