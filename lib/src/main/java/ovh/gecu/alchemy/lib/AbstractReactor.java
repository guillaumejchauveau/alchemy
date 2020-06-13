package ovh.gecu.alchemy.lib;

import org.apache.logging.log4j.Logger;
import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reactor;

public abstract class AbstractReactor implements Reactor {
  protected Cell cell;
  protected State state;

  /**
   * Indicates the state of the reactor.
   *
   * @return The state of the reactor
   */
  @Override
  public State getState() {
    return this.state;
  }

  @Override
  public void setCell(Cell cell) {
    this.cell = cell;
    if (cell != null) {
      this.state = State.IDLE;
    }
  }

  @Override
  public Cell getCell() {
    return this.cell;
  }

  protected abstract void process();

  protected abstract Logger getLogger();

  /**
   * Starts the execution.
   */
  @Override
  public void run() {
    if (this.state != State.IDLE) {
      throw new RuntimeException("Reactor must be idle before running");
    }
    try {
      this.state = State.PROCESSING;
      this.getLogger().info("Starting reactor for cell " + this.cell);
      this.process();
      if (Thread.currentThread().isInterrupted()) {
        this.state = State.INTERRUPTED;
        this.getLogger().warn("Reactor interrupted");
      }
    } catch (Exception e) {
      this.state = State.FAILED;
      this.getLogger().error("An error occurred while processing cell '" + this.cell + "'", e);
      throw e;
    }
  }
}
