package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import ovh.gecu.alchemy.lib.common.solution.ComparableElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Sorts the pipeline's elements based on their values' implementation of
 * {@link Comparable#compareTo}. The first element will be the "greatest" if sort is
 * left-element-sided, the lowest otherwise.
 */
public abstract class Sort<T extends Comparable<T>> extends ComparableReactionPipelineStep<T> {
  private final Element.Side elementSide;

  /**
   * Creates a sort step.
   *
   * @param elementSide The side of the "greatest" element
   */
  public Sort(Element.Side elementSide) {
    this.elementSide = elementSide;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Element> comparableStep(List<ComparableElement<T>> comparableElements, Cell cell) {
    var order = this.elementSide == Element.Side.LEFT ? -1 : 1;
    comparableElements.sort((o1, o2) -> o1.compareTo(o2) * order);
    return new ArrayList<>(comparableElements);
  }
}
