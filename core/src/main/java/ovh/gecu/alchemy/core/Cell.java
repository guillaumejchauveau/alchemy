package ovh.gecu.alchemy.core;

import java.util.Collection;
import java.util.Iterator;

public interface Cell {
  void addElements(Iterator<?> elementIterator);
  void addElements(Iterable<?> elementIterable);
  boolean react();
  Collection<Object> toCollection();
}
