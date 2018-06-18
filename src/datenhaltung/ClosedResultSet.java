package datenhaltung;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static java.sql.Types.*;

public class ClosedResultSet {
    private class Column<T> {
        private String name;
        private ArrayList<T> entries = new ArrayList<>();

        public Column(String name) {
            this.name = name;
        }

        private void addEntry(Object entry) {
            entries.add((T) entry);
        }
    }

    private LinkedList<Column<?>> columns = new LinkedList<>();
//    private LinkedList<Column<Long>> longColumns = new LinkedList<>();
//    private LinkedList<Column<Double>> doubleColumns = new LinkedList<>();
//    private LinkedList<Column<Date>> dateColumns = new LinkedList<>();
//    private LinkedList<Column<String>> stringColumns = new LinkedList<>();

    private int pointer = -1;
    private int maxRows;

    public ClosedResultSet(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                switch (rsmd.getColumnType(i)) {
                    case BIGINT:
                        columns.add(new Column<Long>(rsmd.getColumnLabel(i)));
//                        longColumns.add(new Column<Long>(rsmd.getColumnLabel(i), (Long[]) rs.getArray(i).getArray()));
                        break;
                    case DOUBLE:
                        columns.add(new Column<Double>(rsmd.getColumnLabel(i)));
//                        doubleColumns.add(new Column<Double>(rsmd.getColumnLabel(i), (Double[]) rs.getArray(i).getArray()));
                        break;
                    case TIMESTAMP:
                        columns.add(new Column<Date>(rsmd.getColumnLabel(i)));
//                        dateColumns.add(new Column<Date>(rsmd.getColumnLabel(i), (Date[]) rs.getArray(i).getArray()));
                        break;
                    case VARCHAR:
                        columns.add(new Column<String>(rsmd.getColumnLabel(i)));
//                        stringColumns.add(new Column<String>(rsmd.getColumnLabel(i), (String[]) rs.getArray(i).getArray()));
                        break;
                    default:
                        System.err.println("Unbekannter Datentyp " + rsmd.getColumnType(i));
                }
            }
            while (rs.next()) {
                for (Column<?> column : columns)
                    column.addEntry(rs.getObject(column.name));
                maxRows++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getString(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                return (String) column.entries.get(pointer);
        return "";
    }

    public long getLong(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                if (column.entries.get(pointer).getClass() == BigInteger.class)
                    return ((BigInteger) column.entries.get(pointer)).longValue();
                else
                    return (Long) column.entries.get(pointer);
        return -1;
    }

    public double getDouble(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                return (Double) column.entries.get(pointer);
        return -1;
    }

    public Date getTimestamp(String columnLabel) {
        for (Column<?> column : columns)
            if (column.name.equals(columnLabel))
                return (Date) column.entries.get(pointer);
        return new Date();
    }

    public boolean next() {
        return ++pointer < maxRows;
    }


}
