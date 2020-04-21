package ovh.gecu.alchemy.core.reaction;

import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;

import java.util.List;

/**
 * A single operation in a pipeline.
 */
@FunctionalInterface
public interface ReactionPipelineStep {
  /**
   * Handles the elements in the pipeline. If the returned value is null, the pipeline stops and
   * will fail unless the cell is dissolved.
   *
   * @param inputElements The elements to handle
   * @param cell          The cell in which the reaction occurs
   * @return The elements resulting of the step
   */
  List<Element> handle(List<Element> inputElements, Cell cell);
}
