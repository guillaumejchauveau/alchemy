package ovh.gecu.alchemy.lib.basic;

import ovh.gecu.alchemy.core.Reaction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

final class MethodReaction<R1, R2> implements Reaction<R1, R2> {
  private final Method method;
  private final Boolean isStatic;
  private final Boolean order;

  public MethodReaction(Method method, Boolean order) {
    this.method = method;
    this.isStatic = Modifier.isStatic(method.getModifiers());
    this.order = order;
  }

  @Override
  public Object[] apply(R1 reactant1, R2 reactant2) {
    Object r1, r2;
    if (this.order) {
      r1 = reactant1;
      r2 = reactant2;
    } else {
      r1 = reactant2;
      r2 = reactant1;
    }
    try {
      if (this.isStatic) {
        return (Object[]) this.method.invoke(null, r1, r2);
      }
      return (Object[]) this.method.invoke(r1, r2);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
