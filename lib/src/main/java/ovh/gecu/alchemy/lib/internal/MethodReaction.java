package ovh.gecu.alchemy.lib.internal;

import ovh.gecu.alchemy.core.Reaction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodReaction implements Reaction<Object, Object> {
  private final Method method;
  private final Boolean isStatic;

  public MethodReaction(Method method) {
    this.method = method;
    this.isStatic = Modifier.isStatic(method.getModifiers());
  }

  @Override
  public Object[] apply(Object reactant1, Object reactant2) {
    try {
      if (this.isStatic) {
        return (Object[]) this.method.invoke(null, reactant1, reactant2);
      }
      return (Object[]) this.method.invoke(reactant1, reactant2);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
