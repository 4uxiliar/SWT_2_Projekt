package oberflaeche;

import datenhaltung.AccountDTO;
import datenhaltung.TicketDTO;

import javax.swing.*;

public class MeineTickets extends JPanel {
    private JList<TicketDTO> tickets;
    public MeineTickets(AccountDTO account) {
        this.tickets = new JList<>(account.getTickets());
        add(this.tickets);
    }
}
