package datenhaltung;

public interface IVeranstaltungDAO {
    public VeranstaltungDTO selectById(long id);
    public VeranstaltungDTO[] selectAll();
}
