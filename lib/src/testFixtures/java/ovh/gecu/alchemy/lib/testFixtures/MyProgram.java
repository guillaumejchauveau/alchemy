package ovh.gecu.alchemy.lib.testFixtures;

import ovh.gecu.alchemy.lib.annotation.Reaction;

public class MyProgram {
  @Reaction
  public static Object[] sieve(Integer x, Integer y) {
    // Y divides X so X is consumed.
    if (x % y == 0) {
      return new Object[]{y};
    }
    // X divides Y so Y is consumed.
    if (y % x == 0) {
      return new Object[]{x};
    }
    // Both numbers are prime to each other so they are kept.
    return new Object[]{x, y};
  }
}
