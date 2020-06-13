module ovh.gecu.alchemy.lib {
  requires transitive ovh.gecu.alchemy.core;
  requires com.speedment.common.tuple;
  requires org.apache.logging.log4j;
  requires org.apache.logging.log4j.core;
  exports ovh.gecu.alchemy.lib;
  exports ovh.gecu.alchemy.lib.integer;
  exports ovh.gecu.alchemy.lib.util.log4j_plugins to org.apache.logging.log4j.core;
  provides org.apache.logging.log4j.util.PropertySource with ovh.gecu.alchemy.lib.util.LoggingHelper;
}
