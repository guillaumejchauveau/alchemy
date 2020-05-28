package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Erases the content of the pipeline.
 */
public class ClearPipeline implements ReactionPipelineStep {
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> handle(List<Element> inputElements, Cell cell) {
    return new ArrayList<>();
  }

  @Override
  public String toString() {
    return "ClearPipeline";
  }
}
