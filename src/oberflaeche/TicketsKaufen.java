package oberflaeche;

import datenhaltung.VeranstaltungDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TicketsKaufen extends JPanel {
    private JList<VeranstaltungDTO> veranstaltungenList;
    private MyButton kaufen;

    public TicketsKaufen(VeranstaltungDTO[] veranstaltungen) {
        super(new GridLayout(2, 1));
        this.veranstaltungenList = new JList<>(veranstaltungen);
        this.veranstaltungenList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        kaufen = new MyButton(SprachenController.getInstance().getText("kaufen"));
        kaufen.attach(new Observer() {
            @Override
            public void update() {
                VeranstaltungDTO[] ausgewaehlte = veranstaltungenList.getSelectedValuesList().toArray(new VeranstaltungDTO[0]);
                if (ausgewaehlte.length > 0) {
                    (new KaufenModal((MainFrame) getParent().getParent().getParent().getParent(), ausgewaehlte)).setVisible(true);
                }
            }
        });
        add(this.veranstaltungenList);
        add(kaufen);
    }
}
