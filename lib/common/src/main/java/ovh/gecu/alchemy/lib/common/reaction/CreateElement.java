package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Replace the content of the pipeline with a new element given pre-determined parameters.
 */
public class CreateElement implements ReactionPipelineStep {
  private Constructor<Element> elementConstructor;
  private Object[] args;

  /**
   * Creates an element creation step.
   *
   * @param constructor The {@link Constructor} object used to create the element
   * @param args        The initialization arguments that will be passed to the constructor
   */
  public CreateElement(Constructor<Element> constructor, Object[] args) {
    this.elementConstructor = constructor;
    this.args = args;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> handle(List<Element> inputElements, Cell cell) {
    var outputElements = new ArrayList<Element>();
    try {
      outputElements.add(this.elementConstructor.newInstance(this.args));
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
    return outputElements;
  }

  @Override
  public String toString() {
    return "CreateElement";
  }
}
