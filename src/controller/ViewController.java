package controller;

import misc.InvalidDataException;
import model.Account;
import model.Veranstaltung;
import view.*;

import java.sql.SQLException;

public class ViewController {
    private static ViewController vc;
    private DatenbankController dbc;
    private MainFrame mainFrame;
    private Account account;

    private ViewController() {
        try {
            dbc = DatenbankController.getInstance();
            mainFrame = new MainFrame();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainFrame.getHeader().beobachteMeineTickets(e -> {
            try {
                dbc.ladeGekaufteTickets(account);
                mainFrame.changeView(new MeineTickets());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        mainFrame.getHeader().beobachteTicketsKaufen(e -> {
            try {
                TicketsKaufen view = new TicketsKaufen(dbc.ladeVeranstaltungen());
                view.beobachteKaufen(e1 -> {
                    Veranstaltung[] ausgewaehlte = view.getAusgewaehlteVeranstaltungen();
                    if (ausgewaehlte.length > 0) {
                        KaufenModal kaufenModal = new KaufenModal(mainFrame, ausgewaehlte);
                        kaufenModal.beobachteKaufen(e2 -> {
                            if (ausgewaehlte.length == 1)
                                dbc.kaufeEinzelticket(account, ausgewaehlte[0]);
                            else
                                dbc.kaufeSerienticket(account, ausgewaehlte);
                            kaufenModal.setVisible(false);
                        });
                        kaufenModal.pack();
                        kaufenModal.setVisible(true);
                    }
                });

                mainFrame.changeView(view);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        final LoginModal loginModal = new LoginModal(mainFrame);
        loginModal.beobachteEinloggen(e -> {
                    try {
                        account = dbc.login(loginModal.getCurrentEmail(), loginModal.getCurrentPassword());
                        loginModal.setVisible(false);
                        mainFrame.getHeader().setAccount(account);
                        mainFrame.pack();
                    } catch (InvalidDataException e1) {
                        loginModal.loginFehlgeschlagen();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
        );
        loginModal.setVisible(true);
        //mainFrame = new MainFrame();
    }

    public static ViewController getInstance() {
        if (vc == null)
            vc = new ViewController();
        return vc;
    }

}
