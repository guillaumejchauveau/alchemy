package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Keeps the first ({@link Element.Side#LEFT}) or second ({@link Element.Side#RIGHT}) element of
 * the pipeline.
 */
public class ChooseElement implements ReactionPipelineStep {
  private final Element.Side elementSide;

  /**
   * Creates a reactant choice step.
   *
   * @param elementSide The side of the element
   */
  public ChooseElement(Element.Side elementSide) {
    this.elementSide = elementSide;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> handle(List<Element> inputElements, Cell cell) {
    var output = new ArrayList<Element>();
    output.add(inputElements.get((this.elementSide == Element.Side.LEFT) ? 0 : 1));
    return output;
  }

  @Override
  public String toString() {
    return "ChooseElement";
  }
}
