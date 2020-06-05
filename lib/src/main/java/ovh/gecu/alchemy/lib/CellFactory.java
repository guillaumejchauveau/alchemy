package ovh.gecu.alchemy.lib;

import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;

import java.util.Iterator;

public interface CellFactory {
  void load(Class<?> clazz);

  <R1, R2> CellFactory add(Class<R1> reactantType1, Class<R2> reactantType2, Reaction<R1, R2> reaction);

  CellFactory add(Iterator<?> elementIterator);

  CellFactory add(Iterable<?> elementIterable);

  CellFactory add(Class<?> element, Integer count);

  Cell getCell();
}
