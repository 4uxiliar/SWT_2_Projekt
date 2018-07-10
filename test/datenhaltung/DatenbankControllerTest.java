package datenhaltung;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

class DatenbankControllerTest {

    //@Test
    //public void testeVerbindung()  throws SQLException{
    //    Executable executable = () -> DatenbankController.getInstance().verbinden();
    //    assertThrows(SQLException.class,  executable);
    //}

    @Test
    public void testeVerbindung() throws SQLException {
        try {
            DatenbankController.getInstance().verbinden();
        }
        //Wenn keine Verbindung hergestellt werden kann ist connection null -> null pointer
        catch (NullPointerException n) {
            fail("Datenbank nicht erreichbar");
        }
    }

}