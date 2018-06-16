package oberflaeche;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginModal extends JDialog {
    private JPanel layout;
    private JLabel beschreibung;
    private JTextField email;
    private JTextField password;
    private JButton einloggen;
    private JButton registrieren;

    public LoginModal(JFrame owner) {
        super(owner, "Login", true);
        layout = new JPanel(new GridLayout(5, 1));
        beschreibung = new JLabel("<html><body>Bitte gebe deine Email und dein Passwort ein.<br>Wenn du noch keinen Account hast, dürcke auf registrieren.</body></html>");
        email = new JTextField("admin@admin.com");
        password = new JTextField("password123");
        einloggen = new JButton("Log in");
        registrieren = new JButton("Registrieren");
        layout.add(beschreibung);
        layout.add(email);
        layout.add(password);
        layout.add(einloggen);
        layout.add(registrieren);
        getContentPane().add(layout);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
    }

    public void beobachteEinloggen(ActionListener actionListener) {
        einloggen.addActionListener(actionListener);
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

    public void loginFehlgeschlagen() {
        beschreibung.setText("Die Email oder das Passwort war falsch. Bitte versuche es erneut.");
    }
}
