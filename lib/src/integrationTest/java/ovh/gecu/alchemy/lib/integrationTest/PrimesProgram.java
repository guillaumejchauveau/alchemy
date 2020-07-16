package ovh.gecu.alchemy.lib.integrationTest;

import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.lib.Alchemy;
import ovh.gecu.alchemy.lib.BasicReactor;
import ovh.gecu.alchemy.lib.annotation.Reaction;
import ovh.gecu.alchemy.lib.integer.Range;

public class PrimesProgram {
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
    // Both numbers are prime to each other so nothing happens.
    return null;
  }

  @Test
  public void test() {
    Alchemy.configure(true);
    var primes = Alchemy.newProgram()
      .load(PrimesProgram.class)
      .add(new Range(2, 30))
      .withReactor(new BasicReactor(10000, 5000))
      .run()
      .getCell()
      .getInstanceElements();
    System.out.println(primes);
  }
}
