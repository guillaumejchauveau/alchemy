package ovh.gecu.alchemy.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple reactor implementation.
 */
public class BasicReactor extends AbstractReactor {
  protected final Logger logger;
  protected Integer iterationTarget;
  protected Integer stabilityTarget;

  /**
   * Initializes the reactor with execution settings.
   *
   * @param iterationTarget The maximum number of reactions
   * @param stabilityTarget The maximum number of consecutive no-reactions
   */
  public BasicReactor(Integer iterationTarget, Integer stabilityTarget) {
    this.iterationTarget = iterationTarget;
    this.stabilityTarget = stabilityTarget;
    this.logger = LogManager.getLogger();
    this.state = State.INVALID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    int stability = 0;
    int iteration = 0;
    for (; iteration < this.iterationTarget && !Thread.currentThread().isInterrupted();
         iteration++) {
      if (stability >= this.stabilityTarget) {
        this.state = State.STABLE;
        this.getLogger().info("Solution reached target stability");
        break;
      }

      // No reaction occurred.
      if (!this.cell.react()) {
        stability++;
        // A reaction occurred.
      } else {
        stability = 0;
      }
    }
    this.state = State.STOPPED;
    if (iteration == this.iterationTarget) {
      this.getLogger().info("Iteration target reached");
    }
  }

  @Override
  protected Logger getLogger() {
    return this.logger;
  }
}
