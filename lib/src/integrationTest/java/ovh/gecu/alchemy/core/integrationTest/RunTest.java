package ovh.gecu.alchemy.core.integrationTest;

import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.core.annotation.Reaction;
import ovh.gecu.alchemy.lib.Alchemy;

import java.util.Arrays;

class A {
  @Reaction
  static Object[] yes(A a, B b) {
    return new Object[]{C.class};
  }

}

class B {

}

class C {

}

public class RunTest {
  @Test
  public void test() {
    Alchemy.configure();
    var cell = Alchemy.getFactory()
      .add(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', new StringBuilder()))
      .add(Character.class, StringBuilder.class, (c, s) -> {
        return new Object[]{s.append(c)};
      })
      .add(A.class, 10)
      .add(B.class, 15)
      .add(A.class, B.class, (a, b) -> {
        return new Object[]{C.class};
      })
      .getCell();
    var reactor = Alchemy.getReactor(cell, 10000, 5000);
    reactor.run();
  }
}
