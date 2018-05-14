package view;

import model.Veranstaltung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class KaufenModal extends JDialog {
    private JPanel layout;
    private JLabel beschreibung;
    private JLabel preis;
    private JButton kaufen;
    double gesamtpreis;

    public KaufenModal(JFrame owner, Veranstaltung[] veranstaltungen) {
        super(owner, true);
        String titel, bezeichnung = "<html><body>";
        for (Veranstaltung veranstaltung : veranstaltungen) {
            gesamtpreis += veranstaltung.getPreis();
            bezeichnung += veranstaltung.getEventname() + "<br>";
        }
        bezeichnung += "</body></html>";
        if (veranstaltungen.length == 1)
            titel = "Einzelticket kaufen";
        else {
            titel = "Serienticket kaufen";
            gesamtpreis *= 0.9;
        }
        setTitle(titel);

        layout = new JPanel(new GridLayout(3,1));
        beschreibung = new JLabel(bezeichnung);
        preis = new JLabel("Gesamtpreis: " + gesamtpreis);
        kaufen = new JButton("Kaufen");
        layout.add(beschreibung);
        layout.add(preis);
        layout.add(kaufen);

        add(layout);
        pack();
    }

    public void beobachteKaufen(ActionListener actionListener) {
        kaufen.addActionListener(actionListener);
    }

    public double getPreis() {
        return gesamtpreis;
    }
}
