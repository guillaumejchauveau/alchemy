package ovh.gecu.alchemy.lib.common.reaction;

import ovh.gecu.alchemy.core.solution.Cell;
import ovh.gecu.alchemy.core.solution.Element;
import ovh.gecu.alchemy.utils.tests.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test cases for {@link DissolveCell}.
 */
@Ignore
public class DissolveCellTest extends TestCase {
  @Test
  public void test() {
    var rootCell = new Cell();
    var cell = new Cell();
    rootCell.addSubCell(cell);
    var step = new DissolveCell();
    var el = new Element<>(null) {
    };
    //product.react(el, el);
    Assert.assertTrue(cell.isDissolved());
  }
}
