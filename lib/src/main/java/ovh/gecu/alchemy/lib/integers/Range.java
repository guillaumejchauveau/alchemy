package ovh.gecu.alchemy.lib.integers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An integer generator.
 */
public class Range implements Iterator<Integer> {
  protected Integer start;
  protected Integer stop;
  protected Integer step;
  protected Integer last;

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
    this.last = null;
  }

  @Override
  public boolean hasNext() {
    if (this.last == null) {
      return true;
    }
    if (step > 0) {
      return this.last + this.step < this.stop;
    } else {
      return this.last - this.step > this.stop;
    }
  }

  @Override
  public Integer next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException();
    }
    if (this.last == null) {
      this.last = this.start;
      return this.last;
    }
    if (step > 0) {
      this.last += this.step;
    } else {
      this.last -= this.step;
    }
    return this.last;
  }
}
