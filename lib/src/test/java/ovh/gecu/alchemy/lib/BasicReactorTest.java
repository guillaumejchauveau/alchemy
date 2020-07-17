package ovh.gecu.alchemy.lib;

import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.core.Reaction;
import ovh.gecu.alchemy.core.Reactor;

import static org.junit.jupiter.api.Assertions.*;

public class BasicReactorTest {
  @Test
  @SuppressWarnings("unchecked")
  public void iterationCount() {
    var cell = new BasicCell();
    Reaction<?, ?> r = (Character c, Integer count) -> new Object[] {c, count+1};
    cell.addReactionDefinition(Character.class, Integer.class, (Reaction<Object, Object>) r);
    cell.addElement('a');
    cell.addElement(0);

    var reactor1 = new BasicReactor(0, 10000);
    reactor1.setCell(cell);
    reactor1.run();
    assertEquals(Reactor.State.STOPPED, reactor1.getState());
    assertTrue(cell.instanceElements.contains(0));

    var reactor2 = new BasicReactor(10, 10000);
    reactor2.setCell(cell);
    reactor2.run();
    assertEquals(Reactor.State.STOPPED, reactor2.getState());
    assertTrue(cell.instanceElements.contains(10));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void stabilityCount() {
    var cell = new BasicCell();
    Reaction<?, ?> r = (Character c, Integer count) -> {
      if (count < 5) {
        return new Object[] {c, count+1};
      }
      return null;
    };
    cell.addReactionDefinition(Character.class, Integer.class, (Reaction<Object, Object>) r);
    cell.addElement('a');
    cell.addElement(0);

    var reactor1 = new BasicReactor(10000, 2);
    reactor1.setCell(cell);
    reactor1.run();
    assertEquals(Reactor.State.STABLE, reactor1.getState());
    assertTrue(cell.instanceElements.contains(5));
  }
}
