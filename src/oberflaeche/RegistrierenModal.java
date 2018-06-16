package oberflaeche;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistrierenModal extends JDialog {

    private JPanel layout;
    private JLabel beschreibung;
    private JTextField email;
    private JTextField password;
    private JButton registrieren;

    public RegistrierenModal(JFrame owner) {
        super(owner, "Registrieren", true);
        layout = new JPanel(new GridLayout(5, 1));
        beschreibung = new JLabel("<html><body>Bitte gebe deine Email und dein Passwort ein.<br></body></html>");
        email = new JTextField("Email");
        password = new JTextField("Passwort");
        registrieren = new JButton("Registrieren");
        layout.add(beschreibung);
        layout.add(email);
        layout.add(password);
        layout.add(registrieren);
        getContentPane().add(layout);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
    }

    public void beobachteRegistrieren(ActionListener actionListener) {
        registrieren.addActionListener(actionListener);
    }

    public String getCurrentEmail() {
        return email.getText();
    }

    public String getCurrentPassword() {
        return password.getText();
    }

    public void registrierenFehlgeschlagen() {
        beschreibung.setText("Die Email ist bereits in Verwendung.");
    }


}
