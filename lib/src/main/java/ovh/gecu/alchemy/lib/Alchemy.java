package ovh.gecu.alchemy.lib;

import org.apache.logging.log4j.Level;
import ovh.gecu.alchemy.lib.util.LoggingHelper;

public class Alchemy {
  public static void configure(boolean debug) {
    if (debug) {
      LoggingHelper.configureLoggingFramework(Level.DEBUG, false);
    } else {
      LoggingHelper.configureLoggingFramework(Level.INFO, false);
    }
  }

  public static ProgramFactory getFactory() {
    return new BasicProgramFactory();
  }
}
