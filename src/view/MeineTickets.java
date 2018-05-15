package view;

import model.Account;
import model.Ticket;

import javax.swing.*;

public class MeineTickets extends JPanel {
    private JList<Ticket> tickets;
    public MeineTickets(Account account) {
        this.tickets = new JList<>(account.getTickets());
        add(this.tickets);
    }
}
