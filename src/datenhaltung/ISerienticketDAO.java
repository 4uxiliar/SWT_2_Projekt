package datenhaltung;

import java.util.LinkedList;

public interface ISerienticketDAO {
    public LinkedList<SerienticketDTO> selectAllByBesitzerId(long id);
    public boolean insert(SerienticketDTO serienticketDTO, long accountId);
}
