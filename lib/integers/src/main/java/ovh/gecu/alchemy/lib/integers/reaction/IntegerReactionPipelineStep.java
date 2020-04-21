package ovh.gecu.alchemy.lib.integers.reaction;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import ovh.gecu.alchemy.lib.integers.solution.IntegerElement;

import java.util.ArrayList;
import java.util.List;

/**
 * A base class that checks if all elements are integer elements and casts automatically.
 */
abstract class IntegerReactionPipelineStep implements ReactionPipelineStep {
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> handle(List<Element> inputElements, Cell cell) {
    var integerElements = new ArrayList<IntegerElement>();
    for (var element : inputElements) {
      if (!(element instanceof IntegerElement)) {
        return null;
      }
      integerElements.add((IntegerElement) element);
    }
    return this.integerStep(integerElements, cell);
  }

  /**
   * The method that actually handles the integer elements.
   *
   * @param elements The integer elements to handle. The list is safe from the original pipeline
   *                 input
   * @param cell     The current cell
   * @return The output elements
   */
  protected abstract List<Element> integerStep(List<IntegerElement> elements, Cell cell);
}
