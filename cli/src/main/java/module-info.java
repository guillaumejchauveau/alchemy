module ovh.gecu.alchemy.cli {
  requires ovh.gecu.alchemy.core;
  requires ovh.gecu.alchemy.lib;
  requires ovh.gecu.alchemy.parser;
  requires ovh.gecu.alchemy.utils;
  requires org.apache.logging.log4j;
  requires info.picocli;
  opens ovh.gecu.alchemy.cli to info.picocli;
}
