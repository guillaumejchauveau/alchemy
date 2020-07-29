package ovh.gecu.alchemy.lib;

import org.apache.logging.log4j.Level;
import ovh.gecu.alchemy.lib.util.LoggingHelper;

/**
 * Utility class for the lib Alchemy implementation.
 */
public class Alchemy {
  /**
   * Configures globally utilities used by the implementation.
   *
   * @param debug Configures the internal logging framework to show more details
   */
  public static void configure(boolean debug) {
    if (debug) {
      LoggingHelper.configureLoggingFramework(Level.DEBUG, false);
    } else {
      LoggingHelper.configureLoggingFramework(Level.INFO, false);
    }
  }

  /**
   * Configures globally utilities used by the implementation.
   */
  public static void configure() {
    configure(false);
  }

  /**
   * Starts the creation of a new Alchemy program.
   *
   * @return A new program factory
   * @see ProgramFactory
   */
  public static ProgramFactory newProgram() {
    return new BasicProgramFactory();
  }
}
