package ovh.gecu.alchemy.lib.integers.reaction;

import ovh.gecu.alchemy.lib.common.reaction.Sort;

public class SortIntegers extends Sort<Integer> {
  /**
   * Creates a sort step for integers.
   *
   * @param elementSide The side of the "greatest" element
   */
  public SortIntegers(Element.Side elementSide) {
    super(elementSide);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Integer> getComparisonClass() {
    return Integer.class;
  }

  @Override
  public String toString() {
    return "SortIntegers";
  }
}
