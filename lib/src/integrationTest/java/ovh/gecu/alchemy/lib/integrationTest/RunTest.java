package ovh.gecu.alchemy.lib.integrationTest;

import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.lib.annotation.Reaction;
import ovh.gecu.alchemy.lib.Alchemy;
import ovh.gecu.alchemy.lib.BasicReactor;

import java.util.Arrays;

class A {
  @Reaction
  public static Object[] yes(A a, B b) {
    return new Object[]{C.class, C.class};
  }
}

class B {

}

class C {

}

public class RunTest {
  @Test
  public void test() {
    Alchemy.configure(true);
    var factory = Alchemy.newProgram()
      .add(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', ""))
      .add(Character.class, String.class, (c, s) -> {
        return new Object[]{s + c};
      })
      .add(A.class, 10)
      .add(B.class, 15)
      .load(A.class)
      .withReactor(new BasicReactor(10000, 5000));
      //.run();
    System.out.println(factory);
  }
}
