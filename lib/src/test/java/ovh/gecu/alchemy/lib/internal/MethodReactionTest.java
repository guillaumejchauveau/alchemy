package ovh.gecu.alchemy.lib.internal;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MethodReaction}.
 */
public class MethodReactionTest {
  static class MethodReactionTestClass {
    public static Object[] staticMethodReaction(Integer a, Character b) {
      return new Object[]{a, b};
    }

    public Object[] methodReaction(Integer a) {
      return new Object[]{this, a};
    }
  }

  @Test
  public void staticMethodTest() {
    Method staticMethod;
    try {
      staticMethod = MethodReactionTestClass.class.getMethod("staticMethodReaction", Integer.class, Character.class);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Test case is invalid");
    }

    var reaction = new MethodReaction(staticMethod);
    var result = reaction.apply(1, 'a');
    assertArrayEquals(new Object[]{1, 'a'}, result);
  }

  @Test
  public void invalidParamStaticMethodTest() {
    Method staticMethod;
    try {
      staticMethod = MethodReactionTestClass.class.getMethod("staticMethodReaction", Integer.class, Character.class);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Test case is invalid");
    }

    var reaction = new MethodReaction(staticMethod);
    assertThrows(RuntimeException.class, () -> {
      reaction.apply("Invalid", "Invalid");
    });
  }

  @Test
  public void methodTest() {
    Method method;
    try {
      method = MethodReactionTestClass.class.getMethod("methodReaction", Integer.class);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Test case is invalid");
    }

    var reaction = new MethodReaction(method);
    var reactant1 = new MethodReactionTestClass();
    var result = reaction.apply(reactant1, 1);
    assertArrayEquals(new Object[]{reactant1, 1}, result);
  }

  @Test
  public void invalidParamMethodTest() {
    Method method;
    try {
      method = MethodReactionTestClass.class.getMethod("methodReaction", Integer.class);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Test case is invalid");
    }

    var reaction = new MethodReaction(method);
    assertThrows(RuntimeException.class, () -> {
      reaction.apply("Invalid", "Invalid");
    });
  }
}
