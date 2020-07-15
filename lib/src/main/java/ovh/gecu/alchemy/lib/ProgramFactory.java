package ovh.gecu.alchemy.lib;

import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;
import ovh.gecu.alchemy.core.Reactor;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * An object capable of configuring a cell with its elements and reactions,
 * associating a reactor to the cell and run the program.
 */
public interface ProgramFactory {
  /**
   * Getter for the {@link Cell} configured by the factory.
   *
   * @return The cell of the factory
   */
  Cell getCell();

  /**
   * Getter for the {@link Reactor} associated to the cell (can be null).
   *
   * @return The reactor associated to the cell.
   */
  Reactor getReactor();

  /**
   * Adds elements (normal or counted) to the cell using an {@link Iterator}.
   *
   * @param elementIterator The iterator over the elements
   * @return The factory
   */
  ProgramFactory add(Iterator<?> elementIterator);

  /**
   * Adds elements (normal or counted) to the cell using an {@link Iterable}.
   *
   * @param elementIterable The iterable over the elements
   * @return The factory
   */
  ProgramFactory add(Iterable<?> elementIterable);

  /**
   * Adds elements (normal or counted) to the cell using a {@link Stream}.
   *
   * @param elementStream The stream with the elements
   * @return The factory
   */
  ProgramFactory add(Stream<?> elementStream);

  /**
   * Adds a given quantity of a given counted element.
   *
   * @param element The counted element (a type)
   * @param amount  The quantity to add
   * @return The factory
   */
  ProgramFactory add(Class<?> element, Integer amount);

  /**
   * Adds a {@link Reaction} to the cell by specifying its reactant types.
   *
   * @param reactantType1 The (class) type of the first reactant
   * @param reactantType2 The (class) type of the second reactant
   * @param reaction      The reaction processing the two reactants
   * @param <R1>          The type of the first reactant
   * @param <R2>          The type of the second reactant
   * @return The factory
   * @see Reaction
   */
  <R1, R2> ProgramFactory add(Class<R1> reactantType1, Class<R2> reactantType2, Reaction<R1, R2> reaction);

  /**
   * Adds a {@link Reaction} to the cell using a {@link Method}.
   * If the method is static, it is used like a bi-function. It should have two
   * parameters for the reactants and return an array of objects for the
   * products.
   * If it is non-static, then class declaring the method is considered to be
   * the first reactant type (so it can only be a normal element). The method
   * should have only one parameter for the second reactant, and return an array
   * of objects.
   *
   * @param method The method object to create a reaction from
   * @return The factory
   */
  ProgramFactory load(Method method);

  /**
   * Loads all public methods annotated with {@link ovh.gecu.alchemy.lib.annotation.Reaction}
   * from the given class using {@link ProgramFactory#load(Method)}.
   *
   * @param clazz The class to load the methods from
   * @return The factory
   * @see ovh.gecu.alchemy.lib.annotation.Reaction
   */
  ProgramFactory load(Class<?> clazz);

  /**
   * Associates a reactor to the cell of the factory.
   *
   * @param reactor The reactor
   * @return The factory
   */
  ProgramFactory withReactor(Reactor reactor);

  /**
   * Runs the program formed by the stored {@link Cell} using the associated
   * {@link Reactor}.
   *
   * @return The factory
   * @throws NullPointerException Thrown if no reactors are associated
   */
  default ProgramFactory run() throws NullPointerException {
    this.getReactor().run();
    return this;
  }
}
