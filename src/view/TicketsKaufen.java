package view;

import model.Veranstaltung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TicketsKaufen extends JPanel {
    private JList<Veranstaltung> veranstaltungen;
    private JButton kaufen;

    public TicketsKaufen(Veranstaltung[] veranstaltungen) {
        super(new GridLayout(2, 1));
        this.veranstaltungen = new JList<>(veranstaltungen);
        this.veranstaltungen.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        kaufen = new JButton("Kaufen");
        add(this.veranstaltungen);
        add(kaufen);
    }

    public void beobachteKaufen(ActionListener actionListener) {
        kaufen.addActionListener(actionListener);
    }

    public Veranstaltung[] getAusgewaehlteVeranstaltungen() {
        return this.veranstaltungen.getSelectedValuesList().toArray(new Veranstaltung[0]);
    }
}
