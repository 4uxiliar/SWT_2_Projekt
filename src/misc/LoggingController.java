package misc;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingController {

    private static LoggingController instance;
    private Logger logger;

    public static LoggingController getInstance() {
        if (instance == null)
            instance = new LoggingController();
        return instance;
    }

    private LoggingController() {
        logger = Logger.getAnonymousLogger();
        Level level = Level.parse(ResourceBundle.getBundle("application").getString("logging"));
        logger.setLevel(level);
    }

    public Logger getLogger() {
        return logger;
    }
}
