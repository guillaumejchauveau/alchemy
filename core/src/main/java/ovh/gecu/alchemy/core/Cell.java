package ovh.gecu.alchemy.core;

import java.util.Collection;
import java.util.Map;

/**
 * An isolated Alchemy program, with its reaction rules and current elements.
 * Adding rules and elements is implementation specific. Elements are stored in
 * two different ways: by instance and by quantity. The first is implemented using
 * one instance of the element type for each element stored. Like this, every
 * individual element can have its specific status. In quantity storage, the
 * elements are not instantiated, the cell keep only count of the number of
 * elements for a given element type.
 */
public interface Cell {
  /**
   * Triggers a reaction attempt inside the Cell. Selects two reactants and
   * tries to make them react with each-other.
   *
   * @return True if a reaction was performed. False if the reactants could not
   * react.
   */
  boolean react();

  /**
   * Returns a collection of all elements currently in instance storage.
   */
  Collection<Object> getInstanceElements();

  /**
   * Returns a map associating the types of the elements in quantity storage and
   * their corresponding quantity.
   */
  Map<Class<?>, Integer> getQuantityElements();
}
