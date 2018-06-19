package datenhaltung;

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
      DatenbankController.getInstance().connect();
      throw new SQLException();
    }

}