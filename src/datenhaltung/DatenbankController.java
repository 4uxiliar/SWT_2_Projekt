package datenhaltung;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatenbankController {
    private static DatenbankController dbc;
    private Connection connection;
    private final String username = "root";
    private final String password = "root";
    private final DateFormat dateTime = new SimpleDateFormat("kk:mm:ss dd.MM.yyyy");


    public static DatenbankController getInstance() {
        if (dbc == null)
            dbc = new DatenbankController();
        return dbc;
    }

    private DatenbankController() {
        connect();
        setup();
        disconnect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/ticketshop", username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Der MySQL-Connector kann nicht gladen/gefunden werden. Das Programm terminiert nun.");
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Mit der Datenbank kann nicht verbunden werden. Das Programm terminiert nun.");
            System.exit(1);
        }
    }

    private void disconnect() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        //Creates the tables if they don't already exist.
        final String accountSetup =
                "CREATE TABLE IF NOT EXISTS ACCOUNT(" +
                        "ACCOUNT_ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "EMAIL VARCHAR(100) NOT NULL UNIQUE," +
                        "PASSWORD VARCHAR(100) NOT NULL" +
                        ")";

        final String adresseSetup = "CREATE TABLE IF NOT EXISTS ADRESSE(" +
                "ADRESSE_ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "ORT VARCHAR(100)," +
                "POSTLEITZAHL VARCHAR(5)," +
                "STRASSE VARCHAR(100)," +
                "HAUSNUMMER VARCHAR(10)" +
                ")";

        final String veranstaltungsortSetup = "CREATE TABLE IF NOT EXISTS VERANSTALTUNGSORT(" +
                "VERANSTALTUNGSORT_ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "VERANSTALTUNGSORT_NAME VARCHAR(100)," +
                "ADRESSE BIGINT," +
                "FOREIGN KEY (ADRESSE) REFERENCES ADRESSE(ADRESSE_ID)" +
                ")";

        final String veranstaltungSetup = "CREATE TABLE IF NOT EXISTS VERANSTALTUNG(" +
                "VERANSTALTUNG_ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "EVENTNAME VARCHAR(255)," +
                "PREIS DOUBLE," +
                "VON_DATUM TIMESTAMP," +
                "BIS_DATUM TIMESTAMP," +
                "VERANSTALTUNGSORT BIGINT," +
                "FOREIGN KEY (VERANSTALTUNGSORT) REFERENCES VERANSTALTUNGSORT(VERANSTALTUNGSORT_ID)" +
                ")";

        final String einzelticketSetup = "CREATE TABLE IF NOT EXISTS EINZELTICKET(" +
                "EINZELTICKET_ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "PREIS DOUBLE NOT NULL," +
                "PLATZTYP VARCHAR(10)," +
                "VERANSTALTUNG BIGINT," +
                "BESITZER BIGINT," +
                "FOREIGN KEY (VERANSTALTUNG) REFERENCES VERANSTALTUNG(VERANSTALTUNG_ID)," +
                "FOREIGN KEY (BESITZER) REFERENCES ACCOUNT(ACCOUNT_ID)" +
                ")";

        final String serienticketSetup = "CREATE TABLE IF NOT EXISTS SERIENTICKET(" +
                "SERIENTICKET_ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "PREIS DOUBLE NOT NULL," +
                "BESITZER BIGINT," +
                "FOREIGN KEY (BESITZER) REFERENCES ACCOUNT(ACCOUNT_ID)" +
                ")";

        final String serienticketVeranstaltungSetup = "CREATE TABLE IF NOT EXISTS SERIENTICKET_VERANSTALTUNG(" +
                "SERIENTICKET BIGINT," +
                "VERANSTALTUNG BIGINT," +
                "PLATZTYP VARCHAR(10)," +
                "FOREIGN KEY (SERIENTICKET) REFERENCES SERIENTICKET(SERIENTICKET_ID)," +
                "FOREIGN KEY (VERANSTALTUNG) REFERENCES VERANSTALTUNG(VERANSTALTUNG_ID)," +
                "PRIMARY KEY (SERIENTICKET, VERANSTALTUNG)" +
                ")";
        try {
            Statement statement = connection.createStatement();
            statement.execute(accountSetup);
            statement.execute(adresseSetup);
            statement.execute(veranstaltungsortSetup);
            statement.execute(veranstaltungSetup);
            statement.execute(einzelticketSetup);
            statement.execute(serienticketSetup);
            statement.execute(serienticketVeranstaltungSetup);


            //DUMMY_DATEN
            PreparedStatement varStatement;
            if (!statement.executeQuery("SELECT * FROM ACCOUNT").next()) {
                statement.execute("INSERT INTO ACCOUNT VALUES (NULL, 'admin@admin.com', 'password123')");
            }

            if (!statement.executeQuery("SELECT * FROM ADRESSE").next())
                statement.execute("INSERT INTO ADRESSE VALUES (NULL, 'München', '80939', 'Werner-Heisenberg-Allee', '25')");


            if (!statement.executeQuery("SELECT * FROM VERANSTALTUNGSORT").next())
                statement.execute("INSERT INTO VERANSTALTUNGSORT VALUES (NULL, 'Allianz Arena', 1)");

            varStatement = connection.prepareStatement("INSERT INTO VERANSTALTUNG VALUES (NULL, 'Bayern - Bayer', 10.0, ?, ?, 1)");
            if (!statement.executeQuery("SELECT * FROM VERANSTALTUNG").next()) {
                varStatement.setTimestamp(1, new Timestamp(dateTime.parse("15:30:00 12.05.2018").getTime()));
                varStatement.setTimestamp(2, new Timestamp(dateTime.parse("17:15:00 12.05.2018").getTime()));
                varStatement.execute();
                varStatement = connection.prepareStatement("INSERT INTO VERANSTALTUNG VALUES (NULL, 'Bayern - Dortmund', 10.0, ?, ?, 1)");
                varStatement.setTimestamp(1, new Timestamp(dateTime.parse("15:30:00 19.05.2018").getTime()));
                varStatement.setTimestamp(2, new Timestamp(dateTime.parse("17:15:00 19.05.2018").getTime()));
                varStatement.execute();
            }

            if (!statement.executeQuery("SELECT * FROM EINZELTICKET").next())
                statement.execute("INSERT INTO EINZELTICKET VALUES (NULL, 10, 'SITZPLATZ', 1, 1)");

            if (!statement.executeQuery("SELECT * FROM SERIENTICKET").next())
                statement.execute("INSERT INTO SERIENTICKET VALUES (NULL, 10, 1)");

            if (!statement.executeQuery("SELECT * FROM SERIENTICKET_VERANSTALTUNG").next()) {
                statement.execute("INSERT INTO SERIENTICKET_VERANSTALTUNG VALUES (1, 1, 'STEHPLATZ')");
                statement.execute("INSERT INTO SERIENTICKET_VERANSTALTUNG VALUES (1, 2, 'STEHPLATZ')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public ClosedResultSet executeQuery(String query, Object... params) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 1; i <= params.length; i++)
            statement.setObject(i, params[i - 1]);
        ClosedResultSet crs = new ClosedResultSet(statement.executeQuery());
        disconnect();
        return crs;
    }

    public boolean execute(String query, Object... params) {
        connect();
        boolean executed;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 1; i <= params.length; i++)
                statement.setObject(i, params[i - 1]);
            statement.execute();
            executed= true;
        } catch (SQLException e) {
            executed = false;
        }
        disconnect();
        return executed;
    }
}
