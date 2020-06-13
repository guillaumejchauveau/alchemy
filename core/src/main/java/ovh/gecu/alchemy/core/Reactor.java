package ovh.gecu.alchemy.core;

public interface Reactor extends Runnable {
  enum State {
    INVALID,
    IDLE,
    PROCESSING,
    STABLE,
    STOPPED,
    FAILED,
    INTERRUPTED
  }

  State getState();

  void setCell(Cell cell);

  Cell getCell();
}
