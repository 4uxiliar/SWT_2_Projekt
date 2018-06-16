package oberflaeche;

import datenhaltung.*;
import fachkonzept.Fachkonzept;
import misc.InvalidDataException;

public class ViewController {
    private static ViewController vc;
    private DatenbankController dbc;
    private MainFrame mainFrame;
    private AccountDTO account;
    private Fachkonzept am = new Fachkonzept(new AccountDAOMySQL(), new EinzelticketDAOMySQL(), new SerienticketDAOMySQL(), new VeranstaltungDAOMySQL(), new VeranstaltungsortDAOMySQL(), new AdresseDAOMySQL());
    private ViewController() {
            dbc = DatenbankController.getInstance();
            mainFrame = new MainFrame();
        mainFrame.getHeader().beobachteMeineTickets(e -> {
                am.ladeTickets(account);
                mainFrame.changeView(new MeineTickets(account));
        });

        mainFrame.getHeader().beobachteTicketsKaufen(e -> {
                TicketsKaufen view = new TicketsKaufen(am.ladeVeranstaltungen());
                view.beobachteKaufen(e1 -> {
                    VeranstaltungDTO[] ausgewaehlte = view.getAusgewaehlteVeranstaltungen();
                    if (ausgewaehlte.length > 0) {
                        KaufenModal kaufenModal = new KaufenModal(mainFrame, ausgewaehlte);
                        kaufenModal.beobachteKaufen(e2 -> {
                            if (ausgewaehlte.length == 1)
                                am.kaufeEinzelticket(ausgewaehlte[0]);
                            else
                                am.kaufeSerienticket(ausgewaehlte);
                            kaufenModal.setVisible(false);
                        });
                        kaufenModal.pack();
                        kaufenModal.setVisible(true);
                    }
                });
                mainFrame.changeView(view);
        });
        final LoginModal loginModal = new LoginModal(mainFrame);
        loginModal.beobachteEinloggen(e -> {
                    try {
                        account = am.login(loginModal.getCurrentEmail(), loginModal.getCurrentPassword());
                        loginModal.setVisible(false);
                        mainFrame.getHeader().setAccount(account);
                        mainFrame.pack();
                    } catch (InvalidDataException e1) {
                        loginModal.loginFehlgeschlagen();
                    }
                }
        );
        loginModal.beobachteRegistrieren(e -> {
            RegistrierenModal registrierenModal = new RegistrierenModal(mainFrame);
            registrierenModal.beobachteRegistrieren(e1 -> {
                try {
                    am.registrieren(registrierenModal.getCurrentEmail(), registrierenModal.getCurrentPassword());
                    registrierenModal.setVisible(false);
                    loginModal.setVisible(true);
                } catch (InvalidDataException ex) {
                    registrierenModal.registrierenFehlgeschlagen();
                }
           });
            loginModal.setVisible(false);
            registrierenModal.setVisible(true);
        });
        loginModal.setVisible(true);
    }

    public static ViewController getInstance() {
        if (vc == null)
            vc = new ViewController();
        return vc;
    }

}
