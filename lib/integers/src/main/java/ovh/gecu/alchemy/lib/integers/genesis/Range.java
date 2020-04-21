package ovh.gecu.alchemy.lib.integers.genesis;

import ovh.gecu.alchemy.core.genesis.ElementGenerator;
import ovh.gecu.alchemy.core.solution.Element;
import ovh.gecu.alchemy.lib.integers.solution.IntegerElement;

import java.util.ArrayList;
import java.util.List;

/**
 * An integer element generator.
 */
public class Range implements ElementGenerator {
  private Integer start;
  private Integer stop;
  private Integer step;

  /**
   * Initializes the range generator. Throws an exception if the arguments will create an
   * infinite loop.
   *
   * @param start The value of the first integer
   * @param stop  The end of the range (excluded)
   * @param step  The step of the range
   */
  public Range(Integer start, Integer stop, Integer step) {
    if (step == 0 || step > 0 && stop < start || step < 0 && stop > start || start.equals(stop)) {
      throw new IllegalArgumentException("Given arguments will result in a infinite loop");
    }
    this.start = start;
    this.stop = stop;
    this.step = step;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Element> generate() {
    var outputElement = new ArrayList<Element>();
    for (int i = this.start; (step > 0) ? i < this.stop : i > this.stop; i += this.step) {
      outputElement.add(new IntegerElement(i));
    }
    return outputElement;
  }
}