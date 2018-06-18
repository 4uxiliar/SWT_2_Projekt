package oberflaeche;

import datenhaltung.AccountDTO;
import fachkonzept.Fassade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Header extends JPanel {
    private JPanel navigation;
    private MyButton meineTickets;
    private MyButton ticketsKaufen;
    private JLabel email;

    public Header() {
        super(new BorderLayout());

        navigation = new JPanel();
        meineTickets = new MyButton("Meine Tickets");
        ticketsKaufen = new MyButton("Tickets kaufen");
        navigation.add(meineTickets);
        navigation.add(ticketsKaufen);

        this.email = new JLabel();
        this.email.setMaximumSize(new Dimension(60, 100));
        add(navigation, BorderLayout.WEST);
        add(this.email, BorderLayout.EAST);
        meineTickets.attach(new Observer(){
            @Override
            public void update() {
                AccountDTO account = Fassade.getInstance().getActiveAccount();
                Fassade.getInstance().ladeTickets(account);
                ((MainFrame) getParent().getParent().getParent().getParent()).changeView(new MeineTickets(account));
            }
        });
        ticketsKaufen.attach(new Observer() {
            @Override
            public void update() {
                ((MainFrame) getParent().getParent().getParent().getParent()).changeView(new TicketsKaufen(Fassade.getInstance().ladeVeranstaltungen()));
            }
        });
    }

    public void setAccount(AccountDTO account) {
        email.setText(account.getEmail());
    }

    public void beobachteTicketsKaufen(ActionListener actionListener) {
        ticketsKaufen.addActionListener(actionListener);
    }
}
