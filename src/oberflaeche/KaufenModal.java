package oberflaeche;

import datenhaltung.VeranstaltungDTO;
import fachkonzept.Fassade;

import javax.swing.*;
import java.awt.*;

public class KaufenModal extends JDialog {
    private JPanel layout;
    private JLabel beschreibung;
    private JLabel preis;
    private MyButton kaufen;
    private double gesamtpreis;

    public KaufenModal(JFrame owner, VeranstaltungDTO[] veranstaltungen) {
        super(owner, true);
        String titel, bezeichnung = "<html><body>";
        for (VeranstaltungDTO veranstaltung : veranstaltungen) {
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

        layout = new JPanel(new GridLayout(3, 1));
        beschreibung = new JLabel(bezeichnung);
        preis = new JLabel("Gesamtpreis: " + gesamtpreis);
        kaufen = new MyButton("Kaufen");
        layout.add(beschreibung);
        layout.add(preis);
        layout.add(kaufen);

        kaufen.attach(new Observer() {
            @Override
            public void update() {
                if (veranstaltungen.length > 1)
                    Fassade.getInstance().kaufeSerienticket(veranstaltungen);
                else
                    Fassade.getInstance().kaufeEinzelticket(veranstaltungen[0]);
                setVisible(false);
            }
        });

        add(layout);
        pack();
    }


}
