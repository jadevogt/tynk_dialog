package best.tigers.tynkdialog.util;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Log {

  public static logger log = new logger();

  public static void info(String logMessage) {
    logger.info(logMessage);
  }

  public static void debug(String logMessage) {
    logger.debug(logMessage);
  }

  public static void error(String logMessage) {
    logger.error(logMessage);
  }

  public static void infoOnce(String logMessage) {
    log.infoOnce(logMessage);
  }

  public static class logger {

    private static final Logger logger =
        java.util.logging.Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final ArrayList<String> logOnceMessages;

    public logger() {
      this.logOnceMessages = new ArrayList<>();
    }

    public static void info(String logMessage) {
      logger.info(logMessage);
    }

    public static void debug(String logMessage) {
      logger.fine(logMessage);
    }

    public static void error(String logMessage) {
      logger.severe(logMessage);
    }

    public void infoOnce(String logMessage) {
      if (logOnceMessages.contains(logMessage)) {
        return;
      }
      logOnceMessages.add(logMessage);
      Log.info(logMessage);
    }
  }
}
