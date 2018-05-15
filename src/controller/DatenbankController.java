package controller;

import misc.InvalidDataException;
import model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class DatenbankController {
    private static DatenbankController dbc;
    private Connection connection;
    private boolean connected;
    private final String username = "root";
    private final String password = "root";
    private final DateFormat dateTime = new SimpleDateFormat("kk:mm:ss dd.MM.yyyy");

    public static DatenbankController getInstance() throws SQLException {
        if (dbc == null)
            dbc = new DatenbankController();
        return dbc;
    }

    /**
     * Vorraussetzung: Es muss einen SQL-Server geben, der ein Schema für ticketshop hat (das auf localhost/ticketshop
     * ansprechbar ist. Standardlogin ist momentan username: root password: root gewält, kann aber bei Bedarf abgeändert
     * werden.
     *
     * @throws SQLException
     */
    private DatenbankController() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:mysql://localhost/ticketshop", username, password);
        connected = true;
        try {
            setup();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setup() throws SQLException, ParseException {
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

    }

    public Account login(String email, String password) throws InvalidDataException, SQLException {
        final PreparedStatement loginAbfrage = connection.prepareStatement("SELECT a.ACCOUNT_ID FROM ACCOUNT a WHERE a.EMAIL=? AND a.PASSWORD=?");
        loginAbfrage.setString(1, email);
        loginAbfrage.setString(2, password);
        ResultSet rs = loginAbfrage.executeQuery();
        if (!rs.next())
            throw new InvalidDataException("Falscher Benutzername oder Kennwort.");
        Account account = new Account(rs.getLong("ACCOUNT_ID"));
        ladePersoenlicheDaten(account);
        return account;
    }

    public void ladePersoenlicheDaten(Account account) throws SQLException, InvalidDataException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM ACCOUNT a WHERE ACCOUNT_ID=" + account.getId());
        if (!rs.next())
            throw new InvalidDataException("Der Account konnte nicht gefunden werden. Bitte schließe die Applikation und öffne sie erneut.");
        account.setEmail(rs.getString("EMAIL"));
    }

    public void ladeGekaufteTickets(Account account) throws SQLException {
        Veranstaltung[] veranstaltungen = ladeVeranstaltungen();
        Statement statement = connection.createStatement();
        ResultSet rs;
        ArrayList<Ticket> tickets = new ArrayList<>();

        //Lädt Einzeltickets und fügt sie der Liste hinzu
        rs = statement.executeQuery("SELECT * FROM EINZELTICKET WHERE BESITZER=" + account.getId());
        while (rs.next()) {
            Einzelticket einzelticket = new Einzelticket();
            einzelticket.setTicketnummer(rs.getLong("EINZELTICKET_ID"));
            switch (rs.getString("PLATZTYP")) {
                case "STEHPLATZ":
                    einzelticket.setPlatztyp(Platztyp.STEHPLATZ);
                case "SITZPLATZ":
                    einzelticket.setPlatztyp(Platztyp.SITZPLATZ);
            }
            einzelticket.setPreis(rs.getDouble("PREIS"));
            for (Veranstaltung veranstaltung : veranstaltungen)
                if (veranstaltung.getVeranstaltung_id() == rs.getLong("VERANSTALTUNG")) {
                    einzelticket.setVeranstaltung(veranstaltung);
                }
            tickets.add(einzelticket);
        }

        //Lädt Serientickets und fügt sie der Liste hinzu
        rs = statement.executeQuery("SELECT * FROM SERIENTICKET WHERE BESITZER=" + account.getId());
        while (rs.next()) {
            Serienticket serienticket = new Serienticket();
            serienticket.setTicketnummer(rs.getLong("SERIENTICKET_ID"));
            serienticket.setPreis(rs.getDouble("PREIS"));
            HashMap<Veranstaltung, Platztyp> veranstaltungenPlatztyp = new HashMap<>();
            Statement substatement = connection.createStatement();
            ResultSet subrs = substatement.executeQuery("SELECT VERANSTALTUNG, PLATZTYP FROM SERIENTICKET_VERANSTALTUNG WHERE SERIENTICKET =" + serienticket.getTicketnummer());
            while (subrs.next()) {
                Veranstaltung veranstaltung = null;
                long id = subrs.getLong("VERANSTALTUNG");
                for (Veranstaltung v : veranstaltungen) {
                    if (id == v.getVeranstaltung_id()) {
                        veranstaltung = v;
                        break;
                    }
                }
                veranstaltungenPlatztyp.put(veranstaltung, subrs.getString("PLATZTYP").equals("SITZPLATZ") ? Platztyp.SITZPLATZ : Platztyp.STEHPLATZ);
            }
            serienticket.setVeranstaltungen(veranstaltungenPlatztyp);
            tickets.add(serienticket);
        }
        account.setTickets(tickets.toArray(new Ticket[0]));
    }

    public Veranstaltung[] ladeVeranstaltungen() throws SQLException {
        LinkedList<Veranstaltung> veranstaltungen = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM VERANSTALTUNG v INNER JOIN VERANSTALTUNGSORT vo ON v.VERANSTALTUNGSORT = vo.VERANSTALTUNGSORT_ID INNER JOIN ADRESSE a ON vo.ADRESSE = a.ADRESSE_ID");
        while (rs.next()) {
            Veranstaltung veranstaltung = new Veranstaltung(rs.getLong("VERANSTALTUNG_ID"));
            veranstaltung.setEventname(rs.getString("EVENTNAME"));
            veranstaltung.setPreis(rs.getDouble("PREIS"));
            veranstaltung.setVonDatum(rs.getTimestamp("VON_DATUM"));
            veranstaltung.setBisDatum(rs.getTimestamp("BIS_DATUM"));

            Adresse adresse = new Adresse();
            adresse.setOrt(rs.getString("ORT"));
            adresse.setPlz(rs.getString("POSTLEITZAHL"));
            adresse.setStrasse(rs.getString("STRASSE"));
            adresse.setHausnumer(rs.getString("HAUSNUMMER"));

            Veranstaltungsort veranstaltungsort = new Veranstaltungsort();
            veranstaltungsort.setName(rs.getString("VERANSTALTUNGSORT_NAME"));
            veranstaltungsort.setAdresse(adresse);

            veranstaltung.setVeranstaltungsort(veranstaltungsort);

            veranstaltungen.add(veranstaltung);
        }
        return veranstaltungen.toArray(new Veranstaltung[0]);
    }

    public void kaufeEinzelticket(Account account, Veranstaltung veranstaltung) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO EINZELTICKET VALUES (NULL, 10, 'SITZPLATZ', " + veranstaltung.getVeranstaltung_id() + ", " + account.getId() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kaufeSerienticket(Account account, Veranstaltung[] ausgewaehlte, double preis) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO SERIENTICKET VALUES (NULL, " + preis + ", " + account.getId() + ")");
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
            rs.next();
            long id = rs.getLong("LAST_INSERT_ID()");
            for(Veranstaltung veranstaltung : ausgewaehlte)
                statement.execute("INSERT INTO SERIENTICKET_VERANSTALTUNG VALUES (" + id + ", " + veranstaltung.getVeranstaltung_id() +", 'STEHPLATZ')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrieren(String email, String password) throws InvalidDataException {
        try {
            PreparedStatement preStatement = connection.prepareStatement("INSERT INTO ACCOUNT VALUES (NULL, ?, ?)");
            preStatement.setString(1, email);
            preStatement.setString(2, password);
            preStatement.execute();
        } catch (SQLException e) {
            throw new InvalidDataException("Email bereits in Benutzung");
        }
    }
}
