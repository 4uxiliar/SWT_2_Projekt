package datenhaltung;

import oberflaeche.SprachenController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SerienticketDTO extends TicketDTO {
    private HashMap<VeranstaltungDTO, Platztyp> veranstaltungen = new HashMap<>();

    public SerienticketDTO(long ticketnummer) {
        super(ticketnummer);
    }

    public void setVeranstaltungen(HashMap<VeranstaltungDTO, Platztyp> veranstaltungen) {
        this.veranstaltungen = veranstaltungen;
    }

    public HashMap<VeranstaltungDTO, Platztyp> getVeranstaltungen() {
        return veranstaltungen;
    }

    @Override
    public String toString() {
        SprachenController sc = SprachenController.getInstance();
        String s = "<html><body>" + sc.getText("serienticket") + " " + getPreis() + "<ul>";
        Set<Map.Entry<VeranstaltungDTO, Platztyp>> set = veranstaltungen.entrySet();
        Iterator<Map.Entry<VeranstaltungDTO, Platztyp>> iterator = set.iterator();
        Map.Entry<VeranstaltungDTO, Platztyp> current;
        while (iterator.hasNext()) {
            current = iterator.next();
            s += "<li>" + (current.getValue()==Platztyp.STEHPLATZ?sc.getText("stehplatz"):sc.getText("sitzplatz")) + "    " + current.getKey() + "</li>";
        }
        s += "</ul></body></html>";
        return s;
    }
}
