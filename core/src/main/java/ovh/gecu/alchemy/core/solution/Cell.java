package ovh.gecu.alchemy.core.solution;

import java.util.Collection;
import java.util.Iterator;

public interface Cell {
  void load(Class<?> clazz);
  ElementDefinition getElementDefinition(Class<?> elementType);
  void addElements(Iterator<?> elementIterator);
  void react();
  Collection<Object> toCollection();
}
