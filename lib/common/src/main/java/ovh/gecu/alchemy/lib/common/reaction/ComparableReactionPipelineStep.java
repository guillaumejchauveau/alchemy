package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import ovh.gecu.alchemy.lib.common.solution.ComparableElement;

import java.util.ArrayList;
import java.util.List;

public abstract class ComparableReactionPipelineStep<T extends Comparable<T>>
    implements ReactionPipelineStep {
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> handle(List<Element> inputElements, Cell cell) {
    var comparableElements = new ArrayList<ComparableElement<T>>();
    for (var element : inputElements) {
      if (!(element instanceof ComparableElement)) {
        return null;
      }
      // Checks if the value of the element is comparable with the other elements' value.
      if (!this.getComparisonClass().isInstance(element.evaluate())) {
        return null;
      }
      comparableElements.add((ComparableElement<T>) element);
    }
    return this.comparableStep(comparableElements, cell);
  }

  protected abstract Class<T> getComparisonClass();

  /**
   * The method that actually handles the comparable elements.
   *
   * @param elements The comparable elements to handle. The list is safe from the original pipeline
   *                 input
   * @param cell     The current cell
   * @return The output elements
   */
  protected abstract List<Element> comparableStep(List<ComparableElement<T>> elements, Cell cell);
}
