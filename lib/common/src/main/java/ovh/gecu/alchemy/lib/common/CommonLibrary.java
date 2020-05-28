package ovh.gecu.alchemy.lib.common;

import ovh.gecu.alchemy.core.reaction.ReactionPipelineStep;
import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.lib.InitArgsParser;
import ovh.gecu.alchemy.lib.Library;
import ovh.gecu.alchemy.lib.common.reaction.ChooseElement;
import ovh.gecu.alchemy.lib.common.reaction.ClearPipeline;
import ovh.gecu.alchemy.lib.common.reaction.DissolveCell;
import ovh.gecu.alchemy.lib.common.reaction.EjectInParentCell;
import ovh.gecu.alchemy.lib.common.reaction.Equals;
import ovh.gecu.alchemy.lib.common.reaction.InjectInSubCell;
import ovh.gecu.alchemy.lib.common.reaction.NotEquals;

import java.util.Map;

/**
 * A {@link Library} providing reaction pipeline steps not dependent of the element's type.
 */
public class CommonLibrary extends Library {
  @Override
  public String getName() {
    return "Common";
  }

  @Override
  public Map<String, InitArgsParser<ReactionPipelineStep>> getReactionPipelineSteps() {
    var reactionPipelineSteps = super.getReactionPipelineSteps();

    reactionPipelineSteps.put("choose", args -> {
      if (!(args.length == 1 && args[0] instanceof Element.Side)) {
        throw new IllegalArgumentException(
          "Choose reaction pipeline step requires an element reference");
      }
      return new ChooseElement((Element.Side) args[0]);
    });

    reactionPipelineSteps.put("clear", args -> new ClearPipeline());
    reactionPipelineSteps.put("dissolve", args -> new DissolveCell());
    reactionPipelineSteps.put("equals", args -> new Equals());
    reactionPipelineSteps.put("inject", args -> {
      if (!(args.length == 1 && args[0] instanceof Cell)) {
        throw new IllegalArgumentException(
          "Inject reaction pipeline step requires a cell reference");
      }
      return new InjectInSubCell((Cell) args[0]);
    });

    reactionPipelineSteps.put("eject", args -> new EjectInParentCell());
    reactionPipelineSteps.put("notEquals", args -> new NotEquals());
    return reactionPipelineSteps;
  }
}
