package best.tigers.tynk_dialog.util;

import java.util.logging.Logger;

public class Log {
  final private static Logger logger = java.util.logging.Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public static void info(String logMessage) {
    logger.info(logMessage);
  }

  public static void debug(String logMessage) {
    logger.fine(logMessage);
  }

  public static void error(String logMessage) {
    logger.severe(logMessage);
  }
}
