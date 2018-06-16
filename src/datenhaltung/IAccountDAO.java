package datenhaltung;

public interface IAccountDAO {

    public AccountDTO selectByUsernameAndPassword(String username, String password);
    public boolean insert(AccountDTO email, String password);
}
