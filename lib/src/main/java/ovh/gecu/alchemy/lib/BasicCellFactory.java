package ovh.gecu.alchemy.lib;

import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;
import ovh.gecu.alchemy.lib.internal.MethodReaction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;

class BasicCellFactory implements CellFactory {
  private final BasicCell cell;

  public BasicCellFactory() {
    this.cell = new BasicCell();
  }

  protected <R1, R2> void loadReaction(Method reaction, Class<R1> reactantType1, Class<R2> reactantType2) {
    if (!reaction.getReturnType().equals(Object[].class)) {
      throw new IllegalArgumentException("Reaction method should return Object[]");
    }
    this.cell.addReactionDefinition(reactantType1, reactantType2, new MethodReaction(reaction));
  }

  @Override
  public void load(Class<?> clazz) {
    Arrays.stream(clazz.getMethods())
      .filter(method -> method.isAnnotationPresent(ovh.gecu.alchemy.core.annotation.Reaction.class))
      .forEach(reaction -> {
        Class<?> reactant1Type;
        Class<?> reactant2Type;
        if (Modifier.isStatic(reaction.getModifiers())) {
          if (reaction.getParameterCount() != 2) {
            throw new IllegalArgumentException("Reaction static method must have exactly two parameters for the reactants");
          }
          reactant1Type = reaction.getParameterTypes()[0];
          reactant2Type = reaction.getParameterTypes()[1];
        } else {
          if (reaction.getParameterCount() != 1) {
            throw new IllegalArgumentException("On-element reaction method must have exactly one parameter for the reactant");
          }
          reactant1Type = clazz;
          reactant2Type = reaction.getParameterTypes()[0];
        }
        this.loadReaction(reaction, reactant1Type, reactant2Type);
      });
  }

  @Override
  @SuppressWarnings("unchecked")
  public <R1, R2> CellFactory add(Class<R1> reactantType1, Class<R2> reactantType2, Reaction<R1, R2> reaction) {
    this.cell.addReactionDefinition(reactantType1, reactantType2, (Reaction<Object, Object>) reaction);
    return this;
  }

  @Override
  public CellFactory add(Iterator<?> elementIterator) {
    elementIterator.forEachRemaining(this.cell::addElement);
    return this;
  }

  @Override
  public CellFactory add(Iterable<?> elementIterable) {
    elementIterable.forEach(this.cell::addElement);
    return this;
  }

  @Override
  public CellFactory add(Class<?> element, Integer count) {
    this.cell.addCountedElement(element, count);
    return this;
  }

  @Override
  public Cell getCell() {
    return this.cell;
  }
}
