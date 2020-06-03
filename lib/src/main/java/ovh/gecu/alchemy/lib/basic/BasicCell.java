package ovh.gecu.alchemy.lib.basic;

import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;

import java.util.*;

public class BasicCell implements Cell {
  protected HashMap<Map.Entry<Class<?>, Class<?>>, Reaction<?, ?>> reactionDefinitions;
  protected List<Object> elements;
  protected Random random;

  public BasicCell() {
    this.reactionDefinitions = new HashMap<>();
    this.elements = new ArrayList<>();
    this.random = new Random();
  }

  Map.Entry<Class<?>, Class<?>> makeReactionIdentifier(Class<?> reactantType1, Class<?> reactantType2) {
    return new AbstractMap.SimpleImmutableEntry<>(reactantType1, reactantType2);
  }

  void saveReactionDefinition(Class<?> reactantType1, Class<?> reactantType2, Reaction<?, ?> reaction) {
    var reactionIdentifier = this.makeReactionIdentifier(reactantType1, reactantType2);
    if (this.reactionDefinitions.containsKey(reactionIdentifier)) {
      throw new IllegalArgumentException("Duplicate reaction definition");
    }
    this.reactionDefinitions.put(reactionIdentifier, reaction);
  }

  @Override
  public void addElements(Iterator<?> elementIterator) {
    elementIterator.forEachRemaining(this::addElement);
  }

  @Override
  public void addElements(Iterable<?> elementIterable) {
    elementIterable.forEach(this::addElement);
  }

  protected Reaction<Object, Object> getReaction(Class<?> reactantType1, Class<?> reactantType2) {
    return (Reaction<Object, Object>) this.reactionDefinitions.get(this.makeReactionIdentifier(reactantType1, reactantType2));
  }

  protected synchronized Object pickElement() {
    var elementIndex = this.random.nextInt(this.elements.size());
    var element = this.elements.get(elementIndex);
    this.elements.remove(elementIndex);
    return element;
  }

  protected synchronized void addElement(Object element) {
    this.elements.add(element);
  }

  @Override
  public boolean react() {
    var reactant1 = this.pickElement();
    var reactant2 = this.pickElement();
    var reaction = this.getReaction(reactant1.getClass(), reactant2.getClass());
    if (reaction == null) {
      this.addElement(reactant1);
      this.addElement(reactant2);
      return false;
    }
    this.addElements(Arrays.asList(reaction.apply(reactant1, reactant2)));
    return true;
  }

  @Override
  public Collection<Object> toCollection() {
    return this.elements;
  }
}
