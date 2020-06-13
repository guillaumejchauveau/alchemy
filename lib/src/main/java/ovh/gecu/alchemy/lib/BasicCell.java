package ovh.gecu.alchemy.lib;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuples;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;
import ovh.gecu.alchemy.lib.internal.ReactantInfo;
import ovh.gecu.alchemy.lib.util.ReactionMessage;

import java.util.*;

class BasicCell implements Cell {
  protected Map<Tuple2<? extends Class<?>, ? extends Class<?>>, Reaction<Object, Object>> reactionDefinitions;
  protected List<Object> elements;
  protected Map<Class<?>, Integer> countedElements;
  protected Random random;
  Logger logger;

  public BasicCell() {
    this.reactionDefinitions = new HashMap<>();
    this.elements = new ArrayList<>();
    this.countedElements = new HashMap<>();
    this.random = new Random();
    this.logger = LogManager.getLogger();
  }

  protected void addReactionDefinition(Class<?> reactantType1, Class<?> reactantType2, Reaction<Object, Object> reaction) {
    var reactionIdentifier = Tuples.of(reactantType1, reactantType2);
    if (this.reactionDefinitions.containsKey(reactionIdentifier)) {
      throw new IllegalArgumentException("Duplicate reaction definition");
    }
    this.reactionDefinitions.put(reactionIdentifier, reaction);
  }

  protected void addCountedElement(Class<?> element, Integer count) {
    this.countedElements.put(element, this.countedElements.getOrDefault(element, 0) + count);
  }

  protected void addElement(Object element) {
    if (element instanceof Class) {
      this.addCountedElement((Class<?>) element, 1);
      return;
    }
    this.elements.add(element);
  }

  protected Integer getTotalElementCount() {
    var count = this.elements.size();
    for (var c : this.countedElements.values()) {
      count += c;
    }
    return count;
  }

  private ReactantInfo pickReactant(Integer count) {
    var i = this.random.nextInt(count);
    if (i < this.elements.size()) {
      var element = this.elements.get(i);
      this.elements.remove(i);
      return new ReactantInfo(element);
    } else {
      i -= this.elements.size();
      for (var countedElementCount : this.countedElements.entrySet()) {
        if (i < countedElementCount.getValue()) {
          this.countedElements.put(countedElementCount.getKey(), countedElementCount.getValue() - 1);
          return new ReactantInfo(countedElementCount.getKey());
        }
        i -= countedElementCount.getValue();
      }
    }
    return null;
  }

  protected Tuple2<ReactantInfo, ReactantInfo> pickReactants() {
    var elementCount = this.getTotalElementCount();
    if (elementCount < 2) {
      return null;
    }
    return Tuples.of(this.pickReactant(elementCount), this.pickReactant(elementCount - 1));
  }

  protected Object[] getProducts(Tuple2<ReactantInfo, ReactantInfo> reactants) {
    var reaction = this.reactionDefinitions.get(Tuples.of(reactants.get0().type, reactants.get1().type));
    if (reaction != null) {
      return reaction.apply(reactants.get0().value, reactants.get1().value);
    }
    if (reactants.get0().type != reactants.get1().type) {
      reaction = this.reactionDefinitions.get(Tuples.of(reactants.get1().type, reactants.get0().type));
      if (reaction != null) {
        return reaction.apply(reactants.get1().value, reactants.get0().value);
      }
    }
    return null;
  }

  @Override
  public boolean react() {
    var reactants = this.pickReactants();
    if (reactants == null) {
      return false;
    }

    var products = this.getProducts(reactants);
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

  @Override
  public Collection<Object> getElements() {
    return this.elements;
  }

  @Override
  public Map<Class<?>, Integer> getCountedElements() {
    return this.countedElements;
  }
}
