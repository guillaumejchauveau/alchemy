package ovh.gecu.alchemy.core.solution;

import java.util.*;

public class BasicCell implements Cell {
  protected Map<Class<?>, ElementDefinition> elementDefinitions;
  protected Collection<Object> elements;

  public BasicCell() {
    this.elementDefinitions = new HashMap<>();
    this.elements = new ArrayList<>();
  }

  @Override
  public void load(Class<?> clazz) {
    var elementAnnot = clazz.getAnnotation(Element.class);
    if (elementAnnot != null) {

    }
  }

  @Override
  public ElementDefinition getElementDefinition(Class<?> elementType) {
    return this.elementDefinitions.get(elementType);
  }

  @Override
  public void addElements(Iterator<?> elementIterator) {
    elementIterator.forEachRemaining(this.elements::add);
  }

  @Override
  public void react() {

  }

  @Override
  public Collection<Object> toCollection() {
    return null;
  }
}
