module ovh.gecu.alchemy.lib.common {
  requires ovh.gecu.alchemy.core;
  requires ovh.gecu.alchemy.lib;
  requires ovh.gecu.alchemy.utils;
  exports ovh.gecu.alchemy.lib.common.reaction;
  exports ovh.gecu.alchemy.lib.common.solution;
  provides ovh.gecu.alchemy.lib.Library with ovh.gecu.alchemy.lib.common.CommonLibrary;
}
