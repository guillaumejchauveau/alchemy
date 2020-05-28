package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;

import java.util.List;

/**
 * Dissolves the cell and stops the pipeline.
 */
public class DissolveCell implements ReactionPipelineStep {
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> handle(List<Element> inputElements, Cell cell) {
    cell.dissolve();
    return null;
  }

  @Override
  public String toString() {
    return "DissolveCell";
  }
}
