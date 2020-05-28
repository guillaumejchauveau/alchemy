package ovh.gecu.alchemy.core;

import ovh.gecu.alchemy.core.reaction.Reaction;
import ovh.gecu.alchemy.core.solution.Element;

@Element
public class MyElement {
  @Reaction
  public Object[] oneReaction(MyElement other) {
    return new Object[]{this};
  }
}
