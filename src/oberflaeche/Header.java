package oberflaeche;

import datenhaltung.AccountDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Header extends JPanel {
    private JPanel navigation;
    private JButton meineTickets;
    private JButton ticketsKaufen;
    private JLabel email;

    public Header() {
        super(new BorderLayout());

        navigation = new JPanel();
        meineTickets = new JButton("Meine Tickets");
        ticketsKaufen = new JButton("Tickets kaufen");
        navigation.add(meineTickets);
        navigation.add(ticketsKaufen);

        this.email = new JLabel();
        this.email.setMaximumSize(new Dimension(60, 100));
        add(navigation, BorderLayout.WEST);
        add(this.email, BorderLayout.EAST);
    }

    public void setAccount(AccountDTO account) {
        email.setText(account.getEmail());
    }

    public void beobachteMeineTickets(ActionListener actionListener) {
        meineTickets.addActionListener(actionListener);
    }

    public void beobachteTicketsKaufen(ActionListener actionListener) {
        ticketsKaufen.addActionListener(actionListener);
    }
}
