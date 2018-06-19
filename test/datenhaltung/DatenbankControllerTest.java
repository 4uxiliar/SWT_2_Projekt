package datenhaltung;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatenbankControllerTest {

    //@Test
    //public void testeVerbindung()  throws SQLException{
    //    Executable executable = () -> DatenbankController.getInstance().connect();
    //    assertThrows(SQLException.class,  executable);
    //}

    @Test
    public void testeVerbindung() throws SQLException {
        try {
            DatenbankController.getInstance().connect();
        }
        //Wenn keine Verbindung hergestellt werden kann ist connection null -> null pointer
        catch (NullPointerException n) {
            fail("Datenbank nicht erreichbar");
        }
    }

}