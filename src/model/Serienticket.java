package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Serienticket extends Ticket {
    private HashMap<Veranstaltung, Platztyp> veranstaltungen;

    public void setVeranstaltungen(HashMap<Veranstaltung, Platztyp> veranstaltungen) {
        this.veranstaltungen = veranstaltungen;
    }

    @Override
    public String toString() {
        String s = "<html><body>Serienticket&nbsp;&nbsp;" + getPreis() + "<ul>";
        veranstaltungen.forEach(((veranstaltung, platztyp) ->
        {

        }));
        Set<Map.Entry<Veranstaltung, Platztyp>> set = veranstaltungen.entrySet();
        Iterator<Map.Entry<Veranstaltung, Platztyp>> iterator = set.iterator();
        Map.Entry<Veranstaltung, Platztyp> current;
        while(iterator.hasNext()) {
            current = iterator.next();
            s += "<li>" + current.getValue() + "    " + current.getKey()+"</li>";
        }
        s+="</ul></body></html>";
        return s;
    }
}
