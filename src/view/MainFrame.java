package view;

import model.Veranstaltung;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
    }

    public Header getHeader() {
        return header;
    }

    public void changeView(JPanel newView){
        // TODO Check, whether pack is necessary
        currentView = newView;
        layout.add(currentView, BorderLayout.CENTER);
        pack();
    }
}
