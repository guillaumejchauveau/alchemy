package ovh.gecu.alchemy.lib;

import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;
import ovh.gecu.alchemy.core.Reactor;
import ovh.gecu.alchemy.lib.internal.MethodReaction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * This factory uses a {@link BasicCell}.
 *
 * @inheritDoc
 */
class BasicProgramFactory implements ProgramFactory {
  private final BasicCell cell;
  private Reactor reactor;

  public BasicProgramFactory() {
    this.cell = new BasicCell();
    this.reactor = null;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Cell getCell() {
    return this.cell;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Reactor getReactor() {
    return this.reactor;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ProgramFactory load(Method method) {
    if (!method.getReturnType().equals(Object[].class)) {
      throw new IllegalArgumentException("Reaction method should return Object[]");
    }
    Class<?> reactant1Type;
    Class<?> reactant2Type;
    if (Modifier.isStatic(method.getModifiers())) {
      if (method.getParameterCount() != 2) {
        throw new IllegalArgumentException("Reaction static method must have exactly two parameters for the reactants");
      }
      reactant1Type = method.getParameterTypes()[0];
      reactant2Type = method.getParameterTypes()[1];
    } else {
      if (method.getParameterCount() != 1) {
        throw new IllegalArgumentException("On-element reaction method must have exactly one parameter for the reactant");
      }
      reactant1Type = method.getDeclaringClass();
      reactant2Type = method.getParameterTypes()[0];
    }
    this.cell.addReactionDefinition(reactant1Type, reactant2Type, new MethodReaction(method));
    return this;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ProgramFactory load(Class<?> clazz) {
    Arrays.stream(clazz.getMethods())
      .filter(method -> method.isAnnotationPresent(ovh.gecu.alchemy.lib.annotation.Reaction.class))
      .forEach(this::load);
    return this;
  }

  /**
   * @inheritDoc
   */
  @Override
  @SuppressWarnings("unchecked")
  public <R1, R2> ProgramFactory add(Class<R1> reactantType1, Class<R2> reactantType2, Reaction<R1, R2> reaction) {
    this.cell.addReactionDefinition(reactantType1, reactantType2, (Reaction<Object, Object>) reaction);
    return this;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ProgramFactory add(Class<?> element, Integer amount) {
    this.cell.addCountedElement(element, amount);
    return this;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ProgramFactory add(Iterator<?> elementIterator) {
    elementIterator.forEachRemaining(this.cell::addElement);
    return this;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ProgramFactory add(Iterable<?> elementIterable) {
    elementIterable.forEach(this.cell::addElement);
    return this;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ProgramFactory add(Stream<?> elementStream) {
    elementStream.forEach(this.cell::addElement);
    return this;
  }

  /**
   * @inheritDoc
   */
  @Override
  public ProgramFactory withReactor(Reactor reactor) {
    this.reactor = reactor;
    this.reactor.setCell(this.getCell());
    return this;
  }
}
