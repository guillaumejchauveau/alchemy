package ovh.gecu.alchemy.lib;

import com.speedment.common.tuple.Tuples;
import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.core.Reaction;

import static org.junit.jupiter.api.Assertions.*;

public class BasicCellTest {
  protected static class A {

  }

  protected static class B {

  }

  @Test
  @SuppressWarnings("unchecked")
  public void reactionDefinition() {
    var cell = new BasicCell();
    Reaction<?, ?> reaction = (A a, B b) -> new Object[]{};
    cell.addReactionDefinition(A.class, B.class, (Reaction<Object, Object>) reaction);
    assertTrue(cell.reactionDefinitions.containsKey(Tuples.of(A.class, B.class)));
    assertEquals(reaction, cell.reactionDefinitions.get(Tuples.of(A.class, B.class)));
    assertThrows(IllegalArgumentException.class, () -> {
      cell.addReactionDefinition(A.class, B.class, (Reaction<Object, Object>) reaction);
    }, "Cell should not accept two reactions with the same reactants");
  }
}
