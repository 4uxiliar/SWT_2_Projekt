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
            titel = SprachenController.getInstance().getText("einzelticketKaufen");
        else {
            titel = SprachenController.getInstance().getText("serienticketKaufen");
            gesamtpreis *= 0.9;
        }
        setTitle(titel);

        layout = new JPanel(new GridLayout(3, 1));
        beschreibung = new JLabel(bezeichnung);
        preis = new JLabel(SprachenController.getInstance().getText("gesamtpreis") + ": " + gesamtpreis);
        kaufen = new MyButton(SprachenController.getInstance().getText("kaufen"));
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
