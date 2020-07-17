package ovh.gecu.alchemy.lib;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;
import ovh.gecu.alchemy.lib.util.ReactionMessage;

import java.util.*;

/**
 * A cell implemented using Java Collections. Use the {@link BasicProgramFactory}
 * to configure the cell.
 * This cell is not tested for parallel execution. However, critical sections
 * used by the reactor are synchronized.
 */
public class BasicCell implements Cell {
  protected Map<Tuple2<? extends Class<?>, ? extends Class<?>>, Reaction<Object, Object>> reactionDefinitions;
  protected List<Object> instanceElements;
  protected Map<Class<?>, Integer> quantityElements;
  protected Random random;
  Logger logger;

  /**
   * Creates an empty cell.
   */
  public BasicCell() {
    this.reactionDefinitions = new HashMap<>();
    this.instanceElements = new ArrayList<>();
    this.quantityElements = new HashMap<>();
    this.random = new Random();
    this.logger = LogManager.getLogger();
  }

  /**
   * Adds a reaction to the cell. The reactions are identified with a tuple of
   * their reactant types.
   * Technically, two different reaction of symmetric reactant types (A, B) and
   * (B, A) can be registered. See {@link BasicCell#getProducts(Tuple2)} for
   * more details on the behavior.
   *
   * @param reactantType1 Type (class) of the first reactant
   * @param reactantType2 Type (class) of the second reactant
   * @param reaction      The reaction definition
   */
  protected void addReactionDefinition(Class<?> reactantType1, Class<?> reactantType2, Reaction<Object, Object> reaction) {
    var reactionIdentifier = Tuples.of(reactantType1, reactantType2);
    if (this.reactionDefinitions.containsKey(reactionIdentifier)) {
      throw new IllegalArgumentException("Duplicate reaction definition");
    }
    this.reactionDefinitions.put(reactionIdentifier, reaction);
  }

  /**
   * Adds a given amount of a quantity-stored element.
   *
   * @param element The quantity-stored element to increase the amount of
   * @param amount  The amount to add
   */
  protected void addQuantityElement(Class<?> element, Integer amount) {
    this.quantityElements.put(
      element,
      this.quantityElements.getOrDefault(element, 0) + amount);
  }

  /**
   * Adds an element (instance or quantity stored) to the cell.
   *
   * @param element The element to add
   */
  protected synchronized void addElement(Object element) {
    if (element instanceof Class) {
      this.addQuantityElement((Class<?>) element, 1);
      return;
    }
    this.instanceElements.add(element);
  }

  /**
   * Calculates the total element count.
   *
   * @return The total number of instance and quantity elements in the cell
   */
  protected Integer getTotalElementCount() {
    var count = this.instanceElements.size();
    for (var c : this.quantityElements.values()) {
      count += c;
    }
    return count;
  }

  private ReactantInfo pickReactant(Integer count) {
    var i = this.random.nextInt(count);
    // Pick in the instance elements storage.
    if (i < this.instanceElements.size()) {
      var element = this.instanceElements.get(i);
      this.instanceElements.remove(i);
      return new ReactantInfo(element);
    }
    // Offset the instance elements storage to pick in the quantity element storage.
    i -= this.instanceElements.size();
    // Finds the corresponding quantity element.
    for (var quantityElementCount : this.quantityElements.entrySet()) {
      if (i < quantityElementCount.getValue()) {
        this.quantityElements.put(
          quantityElementCount.getKey(),
          quantityElementCount.getValue() - 1);
        return new ReactantInfo(quantityElementCount.getKey());
      }
      i -= quantityElementCount.getValue();
    }
    return null;
  }

  /**
   * Picks two reactants randomly.
   *
   * @return A pair of reactant info or null if there is less than two elements
   * left in the cell.
   */
  protected synchronized Tuple2<ReactantInfo, ReactantInfo> pickReactants() {
    var elementCount = this.getTotalElementCount();
    if (elementCount < 2) {
      return null;
    }
    return Tuples.of(
      this.pickReactant(elementCount),
      this.pickReactant(elementCount - 1));
  }

  /**
   * Finds the reaction corresponding to the given reactant pair and computes
   * the products.
   * If the reaction for reactants of different type A, B is not registered,
   * tries to find the symmetric reaction for B, A.
   *
   * @param reactants A pair of reactant info
   * @return The products of the reaction or null if no reaction was found
   */
  protected Object[] getProducts(Tuple2<ReactantInfo, ReactantInfo> reactants) {
    var reaction = this.reactionDefinitions.get(
      Tuples.of(reactants.get0().type, reactants.get1().type));
    if (reaction != null) {
      return reaction.apply(reactants.get0().value, reactants.get1().value);
    }
    if (reactants.get0().type != reactants.get1().type) {
      reaction = this.reactionDefinitions.get(
        Tuples.of(reactants.get1().type, reactants.get0().type));
      if (reaction != null) {
        return reaction.apply(reactants.get1().value, reactants.get0().value);
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean react() {
    var reactants = this.pickReactants();
    if (reactants == null) {
      return false;
    }

    var products = this.getProducts(reactants);
    // No reaction occurred.
    if (products == null) {
      this.addElement(reactants.get0().getStorageValue());
      this.addElement(reactants.get1().getStorageValue());
      return false;
    }
    this.logger.debug(new ReactionMessage(reactants, products));
    for (var product : products) {
      this.addElement(product);
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<Object> getInstanceElements() {
    return this.instanceElements;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<Class<?>, Integer> getQuantityElements() {
    return this.quantityElements;
  }
}
