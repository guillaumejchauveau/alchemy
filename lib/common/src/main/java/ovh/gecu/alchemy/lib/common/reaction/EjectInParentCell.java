package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejects the pipeline's content in the cell's parent cell. Then empties the pipeline
 * but does not stop it.
 */
public class EjectInParentCell implements ReactionPipelineStep {
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> handle(List<Element> inputElements, Cell cell) {
    cell.eject(inputElements);
    return new ArrayList<>();
  }

  @Override
  public String toString() {
    return "EjectInParentCell";
  }
}
