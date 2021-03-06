package oberflaeche;

import fachkonzept.Fassade;
import misc.InvalidDataException;

import javax.swing.*;
import java.awt.*;

public class LoginModal extends JDialog {
    private JPanel layout;
    private JLabel beschreibung;
    private JTextField email;
    private JTextField password;
    private MyButton einloggen;
    private MyButton registrieren;

    public LoginModal(JFrame owner) {
        super(owner, "Login", true);
        layout = new JPanel(new GridLayout(5, 1));
        beschreibung = new JLabel(SprachenController.getInstance().getText("eingabeaufforderung"));
        email = new JTextField("admin@admin.com");
        password = new JTextField("password123");
        einloggen = new MyButton("Log in");
        registrieren = new MyButton(SprachenController.getInstance().getText("registrieren"));
        layout.add(beschreibung);
        layout.add(email);
        layout.add(password);
        layout.add(einloggen);
        layout.add(registrieren);
        getContentPane().add(layout);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        einloggen.attach(new Observer() {
            @Override
            public void update() {
                try {
                    Fassade.getInstance().login(email.getText(), password.getText());
                    setVisible(false);
                } catch (InvalidDataException e1) {
                    beschreibung.setText(SprachenController.getInstance().getText("falscheEingabe"));
                }
            }
        });

        registrieren.attach(new Observer() {
            @Override
            public void update() {
                setVisible(false);
                (new RegistrierenModal((MainFrame) getOwner())).setVisible(true);
            }
        });
        pack();
    }

}
