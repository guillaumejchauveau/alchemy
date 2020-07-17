package ovh.gecu.alchemy.lib;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReactantInfoTest {
  @Test
  public void instanceElement() {
    var e1 = "element1";
    var info = new ReactantInfo(e1);
    assertEquals(String.class, info.type);
    assertEquals(e1, info.value);
    assertEquals(e1, info.getStorageValue());
  }

  @Test
  public void quantityElement() {
    var e1 = String.class;
    var info = new ReactantInfo(e1);
    assertEquals(String.class, info.type);
    assertNull(info.value);
    assertEquals(e1, info.getStorageValue());
  }
}
