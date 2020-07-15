package ovh.gecu.alchemy.lib.util.log4j_plugins;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;

/**
 * A Log4J filter that puts it's current thread asleep to slow down the log.
 */
@Plugin(name = "SleepFilter", category = Core.CATEGORY_NAME, elementType = Filter.ELEMENT_TYPE)
public class SleepFilter extends AbstractFilter {
  protected Integer sleepTime;

  /**
   * Plugins are created via the plugin factory.
   *
   * @param sleepTime Time in milli-seconds
   */
  protected SleepFilter(Integer sleepTime) {
    super();
    this.sleepTime = sleepTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Filter.Result filter(LogEvent event) {
    try {
      Thread.sleep(this.sleepTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return super.filter(event);
  }

  @PluginFactory
  public static SleepFilter createFilter(
    @PluginAttribute("time")
      Integer sleepTime,
    @PluginAttribute(AbstractFilterBuilder.ATTR_ON_MATCH)
      Result match,
    @PluginAttribute(AbstractFilterBuilder.ATTR_ON_MISMATCH)
      Result mismatch) {
    return new SleepFilter(sleepTime);
  }
}
