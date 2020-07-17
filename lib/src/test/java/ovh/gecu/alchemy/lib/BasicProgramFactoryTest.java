package ovh.gecu.alchemy.lib;

import com.speedment.common.tuple.Tuples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicProgramFactoryTest {
  static class TestProgram {
    public Object[] testReaction(Integer i) {
      return new Object[0];
    }

    public static Object[] staticTestReaction(Character c, Integer i) {
      return new Object[0];
    }

    public void invalidReaction1(Integer i) {
    }

    public static Object[] invalidReaction2(Integer i) {
      return new Object[0];
    }

    public Object[] invalidReaction3(Character c, Integer i) {
      return new Object[0];
    }
  }

  @Test
  public void loadMethod() {
    var factory = new BasicProgramFactory();

    assertDoesNotThrow(() -> {
      factory.load(TestProgram.class.getMethod("testReaction", Integer.class));
    });
    assertTrue(factory.cell.reactionDefinitions.containsKey(
      Tuples.of(TestProgram.class, Integer.class)));

    assertDoesNotThrow(() -> {
      factory.load(TestProgram.class.getMethod("staticTestReaction", Character.class, Integer.class));
    });
    assertTrue(factory.cell.reactionDefinitions.containsKey(
      Tuples.of(Character.class, Integer.class)));

    assertThrows(IllegalArgumentException.class, () -> {
      factory.load(TestProgram.class.getMethod("invalidReaction1", Integer.class));
    });
    assertThrows(IllegalArgumentException.class, () -> {
      factory.load(TestProgram.class.getMethod("invalidReaction2", Integer.class));
    });
    assertThrows(IllegalArgumentException.class, () -> {
      factory.load(TestProgram.class.getMethod("invalidReaction3", Character.class, Integer.class));
    });
  }
}
