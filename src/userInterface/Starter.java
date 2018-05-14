package userInterface;

import controller.DatenbankController;
import controller.ViewController;

import java.sql.SQLException;

public class Starter {

    public static void main(String[] args) {
        try {
//            MainFrame mainFrame = new MainFrame("test123@lul.com");
            ViewController vc = ViewController.getInstance();
            DatenbankController dbc = DatenbankController.getInstance();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
