package fachkonzept;

import datenhaltung.AccountDTO;
import datenhaltung.IAccountDAO;
import misc.InvalidDataException;

/**Verwaltung des Accounts. Auf die Entity Account.
 *
 */
public class AccountManager {
    private AccountDTO activeAccount;

    private IAccountDAO accountDAO;

    public AccountManager(IAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Meldet den Benutzer an, sofern der Account schon existiert. Sollte er noch nicht existieren oder eine Eingabe
     * falsch sein wird die Fehlermeldung "Falscher Benutzername oder Kennwort" angezeigt.
     * @param email E-Mail des Benutzers
     * @param password Passwort des Benutzers
     * @return
     * @throws InvalidDataException Fehlermeldung bei falschen oder nicht existierenden Eingaben
     */
    public AccountDTO login(String email, String password) throws InvalidDataException {
        activeAccount = accountDAO.selectByUsernameAndPassword(email, password);
        if (activeAccount != null) {
            return activeAccount;
        }
        throw new InvalidDataException("Falscher Benutzername oder Kennwort.");
    }

    /**
     * Erstellt einen Account mit der E-Mailadresse und dem Passwort und fügt sie der Datenbank hinzu.
     * @param email E-Mail für den neuen Account
     * @param password Passwort für den neuen Acoount
     * @throws InvalidDataException wenn das Hinzufügen auf die Datenbank nicht funktioniert hat ( Benutzer existiert bereits)
     */
    public void registrieren(String email, String password) throws InvalidDataException {
        AccountDTO account = new AccountDTO(-1);
        account.setEmail(email);
        if (!accountDAO.insert(account, password))
            throw new InvalidDataException("Dieser Benutzername existiert bereits");
    }

    public AccountDTO getActiveAccount() {
        return activeAccount;
    }
}
