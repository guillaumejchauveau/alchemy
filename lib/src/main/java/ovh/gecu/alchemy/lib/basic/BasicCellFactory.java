package ovh.gecu.alchemy.lib.basic;

import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.CellFactory;
import ovh.gecu.alchemy.core.annotation.Element;
import ovh.gecu.alchemy.core.annotation.Reaction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class BasicCellFactory implements CellFactory {
  protected BasicCell cell;

  public BasicCellFactory() {
    this.cell = new BasicCell();
  }

  protected <R1, R2> void loadReaction(Method reaction, Class<R1> reactantType1, Class<R2> reactantType2) {
    if (!reaction.getReturnType().equals(Object[].class)) {
      throw new IllegalArgumentException("Reaction method should return Object[]");
    }
    this.cell.saveReactionDefinition(reactantType1, reactantType2, new MethodReaction<R1, R2>(reaction, true));
    if (reactantType1 != reactantType2) {
      this.cell.saveReactionDefinition(reactantType2, reactantType1, new MethodReaction<R1, R2>(reaction, false));
    }
  }

  protected void loadElementType(Class<?> elementType) {
    if (Modifier.isAbstract(elementType.getModifiers()) || Modifier.isInterface(elementType.getModifiers())) {
      throw new IllegalArgumentException("Element type must be the actual type used during computation");
    }

    Arrays.stream(elementType.getMethods())
      .filter(method -> !Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(Reaction.class))
      .forEach(reaction -> {
        if (reaction.getParameterCount() != 1) {
          throw new IllegalArgumentException("On-element reaction method must have exactly one parameter for the reactant");
        }
        var reactantType = reaction.getParameterTypes()[0];
        this.loadReaction(reaction, elementType, reactantType);
      });
  }

  public void load(Class<?> clazz) {
    if (clazz.isAnnotationPresent(Element.class)) {
      this.loadElementType(clazz);
    }

    Arrays.stream(clazz.getMethods())
      .filter(method -> Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(Reaction.class))
      .forEach(reaction -> {
        if (reaction.getParameterCount() != 2) {
          throw new IllegalArgumentException("Reaction static method must have exactly two parameters for the reactants");
        }
        var reactant1Type = reaction.getParameterTypes()[0];
        var reactant2Type = reaction.getParameterTypes()[1];
        this.loadReaction(reaction, reactant1Type, reactant2Type);
      });
  }

  @Override
  public Cell getCell() {
    return this.cell;
  }
}
