package oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Das Hauptfenster des Porgramms. Dieses wird niemals ausgetauscht über den gesamten Lebenszyklus. Mit Schließung des Fensters endet das Programm.
 * Das Fenster ist modular aufgebaut und kann neben einer Art Navigationsbar im Header ein JPanel entgegennehmen. In diesem werden die aktuellen
 * Informationen dargestellt
 */
public class MainFrame extends JFrame{
    private JPanel layout;
    private Header header;
    private JPanel currentView;

    public MainFrame(){
        super("Ticketshop");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        layout = new JPanel(new BorderLayout());
        header = new Header();
        layout.add(header, BorderLayout.NORTH);
        layout.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(layout);
        pack();
        setVisible(true);
        (new LoginModal(this)).setVisible(true);
    }

    /**
     * Dient zurm Wechseln der momentan dargestellten Ansicht.
     * Erlaubt die einfache Einbettung neuer Ansichten
     * @param newView Die Ansicht, die dargestellt werden soll.
     */
    public void changeView(JPanel newView){
        if(currentView!=null)
        layout.remove(currentView);
        currentView = newView;
        layout.add(currentView, BorderLayout.CENTER);
        pack();
    }
}
