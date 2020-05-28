package ovh.gecu.alchemy.core.solution;

import java.lang.reflect.Method;

public interface ElementDefinition {
  Class<?> getElementType();

  boolean isEnumerated();

  boolean canReactWith(Class<Object> elementType);

  Method getReaction(Class<Object> elementType);
}
