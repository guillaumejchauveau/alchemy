package ovh.gecu.alchemy.lib.integrationTest;

import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.lib.Alchemy;
import ovh.gecu.alchemy.lib.BasicReactor;
import ovh.gecu.alchemy.lib.annotation.Reaction;

import java.util.Arrays;

public class MixedProgram {
  public static class A {
    @Reaction
    public static Object[] myReaction(A a, B b) {
      return new Object[]{C.class, C.class};
    }
  }

  public static class B {

  }

  public static class C {

  }

  @Test
  public void test() {
    Alchemy.configure(true);
    var cell = Alchemy.newProgram()
      .add(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', ""))
      .add(Character.class, String.class, (c, s) -> new Object[]{s + c})
      .add(A.class, 5)
      .add(B.class, 8)
      .load(A.class)
      .withReactor(new BasicReactor(10000, 5000))
      .run()
      .getCell();
    System.out.println(cell.getInstanceElements());
    System.out.println(cell.getQuantityElements());
  }
}
