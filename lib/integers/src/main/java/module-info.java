module ovh.gecu.alchemy.lib.integers {
  requires ovh.gecu.alchemy.core;
  requires ovh.gecu.alchemy.lib;
  requires ovh.gecu.alchemy.lib.common;
  requires ovh.gecu.alchemy.utils;
  provides ovh.gecu.alchemy.lib.Library with ovh.gecu.alchemy.lib.integers.IntegerLibrary;
}
