package ovh.gecu.alchemy.lib.util;

import com.speedment.common.tuple.Tuple2;
import org.apache.logging.log4j.message.FormattedMessage;
import ovh.gecu.alchemy.lib.internal.ReactantInfo;

import java.util.Arrays;

/**
 * Handles messages for a reaction. Formats a string with the reactants and the
 * products.
 */
public class ReactionMessage extends FormattedMessage {
  public ReactionMessage(Tuple2<ReactantInfo, ReactantInfo> reactants, Object[] products) {
    super(
      "({}, {}) -> {{}}",
      createReactantString(reactants.get0()),
      createReactantString(reactants.get1()),
      Arrays.stream(products).reduce("", (s, product) -> {
        ReactantInfo info;
        if (product instanceof Class) {
          info = new ReactantInfo((Class<?>) product);
        } else {
          info = new ReactantInfo(product);
        }
        if (!((String) s).isEmpty()) {
          s = s + ", ";
        }
        return s + createReactantString(info);
      })
    );
  }

  /**
   * Formats a string representing a reactant.
   * @param reactantInfo Data concerning the reactant
   * @return The formatted string
   */
  protected static String createReactantString(ReactantInfo reactantInfo) {
    var s = new StringBuilder();
    if (reactantInfo.value != null) {
      try {
        if (reactantInfo.type.getMethod("toString").getDeclaringClass() != Object.class) {
          s.append(reactantInfo.value.toString()).append(" ");
        }
      } catch (ReflectiveOperationException ignored) {
      }
    }
    s.append("<").append(reactantInfo.type.getSimpleName()).append(">");
    return s.toString();
  }
}
