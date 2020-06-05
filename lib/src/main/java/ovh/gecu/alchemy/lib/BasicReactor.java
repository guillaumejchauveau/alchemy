package ovh.gecu.alchemy.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reactor;

/**
 * Makes a cell's elements react according to the cell's reaction pipelines.
 */
class BasicReactor implements Reactor {
  protected final Logger logger;
  protected Cell cell;
  protected Integer iterationTarget;
  protected Integer stabilityTarget;
  protected State state;

  /**
   * Initializes the reactor with execution settings.
   *
   * @param cell            The cell to execute
   * @param iterationTarget The maximum number of reactions
   * @param stabilityTarget The maximum number of consecutive no-reactions
   */
  public BasicReactor(Cell cell, Integer iterationTarget, Integer stabilityTarget) {
    this.cell = cell;
    this.iterationTarget = iterationTarget;
    this.stabilityTarget = stabilityTarget;
    this.logger = LogManager.getLogger();
    this.state = State.IDLE;
  }

  /**
   * Indicates the state of the reactor.
   *
   * @return The state of the reactor
   */
  @Override
  public State getState() {
    return this.state;
  }

  /**
   * Executes the program with the pre-configured parameters.
   */
  protected void process() {
    int stability = 0;
    int iteration = 0;
    for (; iteration < this.iterationTarget && !Thread.currentThread().isInterrupted();
         iteration++) {
      if (stability >= this.stabilityTarget) {
        this.state = State.STABLE;
        this.logger.info("Solution reached target stability");
        break;
      }

      if (!this.cell.react()) {
        stability++;
      }
    }
    this.state = State.STOPPED;
    if (iteration == this.iterationTarget) {
      this.logger.info("Iteration target reached");
    }
  }

  /**
   * Starts the execution.
   */
  @Override
  public void run() {
    try {
      this.state = State.PROCESSING;
      this.logger.info("Starting reactor for cell " + this.cell);
      this.process();
      if (Thread.currentThread().isInterrupted()) {
        this.state = State.INTERRUPTED;
        this.logger.warn("Reactor interrupted");
      }
    } catch (Exception e) {
      this.state = State.FAILED;
      this.logger.error("An error occurred while processing cell '" + this.cell + "'", e);
      throw e;
    }
  }
}
