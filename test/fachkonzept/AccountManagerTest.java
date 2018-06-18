package fachkonzept;

import datenhaltung.AccountDAOMySQL;
import datenhaltung.AccountDTO;
import datenhaltung.IAccountDAO;
import datenhaltung.VeranstaltungDTO;
import misc.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AccountManagerTest {
    private AccountDTO account;
    private IAccountDAO accountDAO = mock(AccountDAOMySQL.class);
    private AccountManager accountManager = new AccountManager(accountDAO);

    @BeforeEach
    public void setUp() {
        account = new AccountDTO(-1);
        account.setEmail("test");
    }

    @Test
    public void testLogin() throws InvalidDataException {
        when(accountDAO.selectByUsernameAndPassword("test", "test")).thenReturn(account);
        assertEquals(account, accountManager.login("test", "test"));
    }

    @Test
    public void testLoginFail() {
        when(accountDAO.selectByUsernameAndPassword("test", "test")).thenReturn(null);
        Executable executable = ()->accountManager.login("test", "test");
        assertThrows(InvalidDataException.class, executable);
    }

    @Test
    public void testRegistrierenFail() {
        when(accountDAO.insert(any(AccountDTO.class),anyString())).thenReturn(false);
        Executable executable = () -> accountManager.registrieren("test", "test");
        assertThrows(InvalidDataException.class, executable);
    }

}