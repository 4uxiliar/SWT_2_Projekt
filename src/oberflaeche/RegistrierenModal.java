package oberflaeche;

import fachkonzept.Fassade;
import misc.InvalidDataException;

import javax.swing.*;
import java.awt.*;

public class RegistrierenModal extends JDialog {

    private JPanel layout;
    private JLabel beschreibung;
    private JTextField email;
    private JTextField password;
    private MyButton registrieren;

    public RegistrierenModal(JFrame owner) {
        super(owner, "Registrieren", true);
        layout = new JPanel(new GridLayout(5, 1));
        beschreibung = new JLabel("<html><body>Bitte gebe deine Email und dein Passwort ein.<br></body></html>");
        email = new JTextField("Email");
        password = new JTextField("Passwort");
        registrieren = new MyButton("Registrieren");
        layout.add(beschreibung);
        layout.add(email);
        layout.add(password);
        layout.add(registrieren);
        getContentPane().add(layout);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        registrieren.attach(new Observer() {
            @Override
            public void update() {
                try {
                    Fassade.getInstance().registrieren(email.getText(), password.getText());
                    setVisible(false);
                    (new LoginModal((MainFrame) getOwner())).setVisible(true);
                } catch (InvalidDataException e) {
                    beschreibung.setText("Die Email ist bereits in Verwendung.");
                }
            }
        });
        pack();
    }
}
