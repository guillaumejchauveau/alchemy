package ovh.gecu.alchemy.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.core.Reactor;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractReactorTest {
  static class ReactorMock extends AbstractReactor {
    public boolean processCalled;
    public boolean doThrow;

    public ReactorMock() {
      this.processCalled = false;
      this.doThrow = false;
      this.state = State.INVALID;
    }

    @Override
    protected void process() {
      this.processCalled = true;
      if (this.doThrow) {
        throw new RuntimeException("hello");
      }
    }

    @Override
    protected Logger getLogger() {
      return LogManager.getLogger();
    }
  }

  @Test
  public void test() {
    var reactor = new ReactorMock();
    assertThrows(IllegalStateException.class, reactor::run);
    reactor.setCell(new BasicCell());
    assertEquals(Reactor.State.IDLE, reactor.getState());
    assertDoesNotThrow(reactor::run);
    assertTrue(reactor.processCalled);
    assertEquals(Reactor.State.PROCESSING, reactor.getState());
    reactor.processCalled = false;
    reactor.doThrow = true;
    reactor.setCell(new BasicCell());
    assertThrows(RuntimeException.class, reactor::run);
    assertEquals(Reactor.State.FAILED, reactor.getState());

    reactor.setCell(null);
    assertEquals(Reactor.State.INVALID, reactor.getState());
  }
}
