package datenhaltung;

import misc.LoggingController;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import static java.sql.Types.*;

/**
 * Eine Hilfsklasse, die eine Statement-ungebundene Implementation des ResultSets darstellt.
 */
public class ClosedResultSet {
    private class Column<T> {
        private String name;
        private ArrayList<T> eintraege = new ArrayList<>();

        public Column(String name) {
            this.name = name;
        }

        private void addEintrag(Object eintrag) {
            eintraege.add((T) eintrag);
        }
    }

    private LinkedList<Column<?>> columns = new LinkedList<>();

    private int zeiger = -1;
    private int maxZeilen;

    /**
     * Erstellt zu einem gegebenen ResultSet ein äquivalentes ClosedResultSet
     * @param rs
     */
    public ClosedResultSet(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                switch (rsmd.getColumnType(i)) {
                    case BIGINT:
                        columns.add(new Column<Long>(rsmd.getColumnLabel(i)));
                        break;
                    case DOUBLE:
                        columns.add(new Column<Double>(rsmd.getColumnLabel(i)));
                        break;
                    case TIMESTAMP:
                        columns.add(new Column<Date>(rsmd.getColumnLabel(i)));
                        break;
                    case VARCHAR:
                        columns.add(new Column<String>(rsmd.getColumnLabel(i)));
                        break;
                    default:
                        LoggingController.getInstance().getLogger().warning("Unbekannter Datentyp " + rsmd.getColumnType(i));
                }
            }
            while (rs.next()) {
                for (Column<?> column : columns)
                    column.addEintrag(rs.getObject(column.name));
                maxZeilen++;
            }
        } catch (SQLException e) {
            LoggingController.getInstance().getLogger().severe(e.getMessage());
        }
    }

    public String getString(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                return (String) column.eintraege.get(zeiger);
        return "";
    }

    public long getLong(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                if (column.eintraege.get(zeiger).getClass() == BigInteger.class)
                    return ((BigInteger) column.eintraege.get(zeiger)).longValue();
                else
                    return (Long) column.eintraege.get(zeiger);
        return -1;
    }

    public double getDouble(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                return (Double) column.eintraege.get(zeiger);
        return -1;
    }

    public Date getTimestamp(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                return (Date) column.eintraege.get(zeiger);
        return new Date();
    }

    public boolean next() {
        return ++zeiger < maxZeilen;
    }


}
