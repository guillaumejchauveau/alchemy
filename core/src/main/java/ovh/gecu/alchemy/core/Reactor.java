package ovh.gecu.alchemy.core;

/**
 * An object responsible of executing an Alchemy program.
 */
public interface Reactor extends Runnable {
  /**
   * A reactor can be in one of the following states.
   */
  enum State {
    /**
     * Reactor is not properly configured.
     */
    INVALID,
    /**
     * Reactor is ready.
     */
    IDLE,
    /**
     * Reactor is executing the program.
     */
    PROCESSING,
    /**
     * Reactor has stopped the execution as no more reactions can be performed.
     */
    STABLE,
    /**
     * Reactor has stopped for an expected reason.
     */
    STOPPED,
    /**
     * Reactor has stopped for an unexpected reason.
     */
    FAILED,
    /**
     * Reactor has stopped because its thread was interrupted.
     */
    INTERRUPTED
  }

  /**
   * Returns the current state of the reactor.
   */
  State getState();

  /**
   * Sets the program to be executed by the reactor.
   */
  void setCell(Cell cell);

  /**
   * Returns the program executed by the reactor.
   */
  Cell getCell();
}
