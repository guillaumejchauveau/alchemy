package ovh.gecu.alchemy.core;

import java.util.Collection;
import java.util.Map;

public interface Cell {
  boolean react();
  Collection<Object> getElements();
  Map<Class<?>, Integer> getCountedElements();
}
