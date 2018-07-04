package oberflaeche;

import java.util.Locale;
import java.util.ResourceBundle;

public class SprachenController {
    private static SprachenController instance;
    ResourceBundle rb;


    public static SprachenController getInstance() {
        if(instance==null)
            instance=new SprachenController();
        return instance;
    }

    private SprachenController() {
        ResourceBundle rb = ResourceBundle.getBundle("application");
        Locale locale = Locale.getDefault();
        switch (rb.getString("sprache")){
            case "DEUTSCH":
                locale = Locale.GERMAN;
                break;
            case "ENGLISCH":
                locale = Locale.ENGLISH;
                break;
        }
        this.rb = ResourceBundle.getBundle("LanguageBundle", locale);
    }

    public String getText(String schluessel) {
    return rb.getString(schluessel);
    }
}
