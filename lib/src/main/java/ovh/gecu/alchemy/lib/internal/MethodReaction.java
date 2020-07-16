package ovh.gecu.alchemy.lib.internal;

import ovh.gecu.alchemy.core.Reaction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * A reaction implemented using a class method (can be static).
 * The method will be invoked with the first reactant as the instance for the
 * method (so the method should be on the reactant's type's class) and the
 * second reactant the argument. If the method is static, both reactants are
 * arguments.
 * If the first reactant is suppose to be quantity-stored, the method has to be
 * static as the reactant will not be an instance, so cannot be used to call a
 * non-static method.
 */
public class MethodReaction implements Reaction<Object, Object> {
  private final Method method;
  private final Boolean isStatic;

  /**
   * Creates a method reaction using a given Method object.
   *
   * @param method The method to invoke as the reaction.
   */
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
