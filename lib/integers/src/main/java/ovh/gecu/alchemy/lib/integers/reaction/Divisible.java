package ovh.gecu.alchemy.lib.integers.reaction;

import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import ovh.gecu.alchemy.lib.integers.solution.IntegerElement;

import java.util.ArrayList;
import java.util.List;

public class Divisible extends IntegerReactionPipelineStep {
  private Element.Side elementSide;

  public Divisible(Element.Side elementSide) {
    this.elementSide = elementSide;
  }

  @Override
  protected List<Element> integerStep(List<IntegerElement> elements, Cell cell) {
    var output = new ArrayList<Element>();
    if (elements.get(0).evaluate() % elements.get(1).evaluate() == 0) {
      output.add(elements.get(0));
      output.add(elements.get(1));
    } else if (elements.get(1).evaluate() % elements.get(0).evaluate() == 0) {
      output.add(elements.get(1));
      output.add(elements.get(0));
    } else {
      return null;
    }
    if (this.elementSide == Element.Side.RIGHT) {
      var tmp = output.get(0);
      output.remove(0);
      output.add(tmp);
    }
    return output;
  }

  @Override
  public String toString() {
    return "Divisible";
  }
}
