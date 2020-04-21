module ovh.gecu.alchemy.core {
  requires ovh.gecu.alchemy.utils;
  requires org.apache.logging.log4j;
  exports ovh.gecu.alchemy.core.solution;
  exports ovh.gecu.alchemy.core.reaction;
  exports ovh.gecu.alchemy.core.reactor;
  exports ovh.gecu.alchemy.core.genesis;
}
