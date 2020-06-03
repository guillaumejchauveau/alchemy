package ovh.gecu.alchemy.core;

public interface Reactor extends Runnable {
  enum State {
    IDLE,
    PROCESSING,
    STABLE,
    STOPPED,
    FAILED,
    INTERRUPTED
  }

  State getState();
}
