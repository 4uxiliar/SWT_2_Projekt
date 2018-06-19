package datenhaltung;

import java.sql.SQLException;

public class AccountDAOMySQL implements IAccountDAO {
    @Override
    public AccountDTO selectByUsernameAndPassword(String email, String password) {
        AccountDTO account = null;
        try {
            ClosedResultSet crs = DatenbankController.getInstance().executeQuery("SELECT * FROM ACCOUNT a WHERE a.EMAIL=? AND a.PASSWORD=?", email, password);
            if (crs.next()) {
                account = new AccountDTO(crs.getLong("ACCOUNT_ID"));
                account.setEmail(crs.getString("EMAIL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public boolean insert(AccountDTO account, String password) {
        return DatenbankController.getInstance().execute("INSERT INTO ACCOUNT VALUES (NULL, ?, ?)", account.getEmail(), password);
    }
}
