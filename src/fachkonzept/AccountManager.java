package fachkonzept;

import datenhaltung.AccountDTO;
import datenhaltung.IAccountDAO;
import misc.InvalidDataException;

public class AccountManager {
    private AccountDTO activeAccount;

    private IAccountDAO accountDAO;

    public AccountManager(IAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public AccountDTO login(String email, String password) throws InvalidDataException {
        activeAccount = accountDAO.selectByUsernameAndPassword(email, password);
        if (activeAccount != null) {
            Fassade.getInstance().ladeTickets(activeAccount);
            return activeAccount;
        }
        throw new InvalidDataException("Falscher Benutzername oder Kennwort.");
    }

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
