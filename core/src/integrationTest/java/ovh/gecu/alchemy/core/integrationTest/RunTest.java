package ovh.gecu.alchemy.core.integrationTest;

import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.lib.basic.BasicCellFactory;
import ovh.gecu.alchemy.lib.basic.BasicReactor;
import ovh.gecu.alchemy.core.testFixtures.MyProgram;
import ovh.gecu.alchemy.lib.integers.Range;

public class RunTest {
  @Test
  public void test() {
    var factory = new BasicCellFactory();
    factory.load(MyProgram.class);
    var cell = factory.getCell();
    cell.addElements(new Range(2, 80, 1));
    var reactor = new BasicReactor(cell, 10000, 500);
    reactor.run();
  }
}
