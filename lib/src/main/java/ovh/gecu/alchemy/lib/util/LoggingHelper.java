package ovh.gecu.alchemy.lib.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.PropertySource;

/**
 * Utility class to configure Log4J.
 */
public class LoggingHelper implements PropertySource {
  private static final String PREFIX = "log4j2.";
  private static Boolean isConfigured = false;

  /**
   * Configures Log4J.
   *
   * @param level Logging level
   * @param showThread Print the thread emitting the logs in the console
   */
  public static void configureLoggingFramework(Level level, boolean showThread) {
    if (LoggingHelper.isConfigured) {
      return;
    }
    var pattern = "";
    if (showThread) {
      pattern = "%style{%thread}{underline} ";
    }
    pattern += "%highlight{%-5level}{";
    pattern += "FATAL=red bright, ERROR=red, WARN=yellow, INFO=blue, DEBUG=cyan, TRACE=white} ";
    pattern += "%style{%c{1}}{bright}: %msg{ansi}%n%style{%throwable}{white}";

    var builder = ConfigurationBuilderFactory.newConfigurationBuilder();
    var appenderBuilder = builder.newAppender("StdERR", ConsoleAppender.PLUGIN_NAME)
      .addAttribute("target", ConsoleAppender.Target.SYSTEM_ERR);
    var layout = builder.newLayout("PatternLayout")
      .addAttribute("pattern", pattern);
    appenderBuilder.add(layout);
    builder.add(appenderBuilder);
    builder.add(builder.newRootLogger(level).add(builder.newAppenderRef("StdERR")));

    Configurator.initialize(builder.build());
    var logger = LogManager.getLogger();
    logger.trace("Logging framework configured");
    LoggingHelper.isConfigured = true;
  }

  public static void configureLoggingFramework(Level level) {
    configureLoggingFramework(level, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPriority() {
    return -200;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void forEach(BiConsumer<String, String> action) {
    action.accept("log4j2.skipJansi", "false");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharSequence getNormalForm(Iterable<? extends CharSequence> tokens) {
    return PREFIX + PropertySource.Util.joinAsCamelCase(tokens);
  }
}
