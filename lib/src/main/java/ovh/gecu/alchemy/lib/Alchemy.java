package ovh.gecu.alchemy.lib;

import org.apache.logging.log4j.Level;
import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reactor;
import ovh.gecu.alchemy.lib.util.LoggingHelper;

public class Alchemy {
  public static void configure() {
    LoggingHelper.configureLoggingFramework(Level.DEBUG);
  }

  public static CellFactory getFactory() {
    return new BasicCellFactory();
  }

  public static Reactor getReactor(Cell cell, Integer iterationTarget, Integer stabilityTarget) {
    return new BasicReactor(cell, iterationTarget, stabilityTarget);
  }

  public static Reactor getReactor(CellFactory cellFactory, Integer iterationTarget, Integer stabilityTarget) {
    return Alchemy.getReactor(cellFactory.getCell(), iterationTarget, stabilityTarget);
  }
}
