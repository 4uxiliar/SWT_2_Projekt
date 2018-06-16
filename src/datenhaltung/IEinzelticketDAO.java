package datenhaltung;

import java.util.LinkedList;

public interface IEinzelticketDAO {
    public LinkedList<EinzelticketDTO> selectAllByBesitzerId(long id);
    public boolean insert(EinzelticketDTO einzelticket, long accountId);
}
