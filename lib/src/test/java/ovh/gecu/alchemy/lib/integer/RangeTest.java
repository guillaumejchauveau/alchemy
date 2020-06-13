package ovh.gecu.alchemy.lib.integer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class RangeTest {
  @Test
  public void range() {
    var range = new Range(0, 5);
    assertIterableEquals(Arrays.asList(0, 1, 2, 3, 4), range);
  }

  @Test
  public void negativeRange() {
    var range = new Range(-3, 2);
    assertIterableEquals(Arrays.asList(-3, -2, -1, 0, 1), range);
  }

  @Test
  public void reverseRange() {
    var range = new Range(2, -3, -1);
    assertIterableEquals(Arrays.asList(2, 1, 0, -1, -2), range);
  }

  @Test
  public void stepRange() {
    var range = new Range(0, 10, 2);
    assertIterableEquals(Arrays.asList(0, 2, 4, 6, 8), range);
  }

  @Test
  public void reverseStepRange() {
    var range = new Range(5, -3, -2);
    assertIterableEquals(Arrays.asList(5, 3, 1, -1), range);
  }

  @Test
  public void illegalArguments() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Range(0, 0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Range(0, 0, 1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Range(4, 0, 1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Range(-4, 0, -1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Range(0, 10, 0);
    });
  }

  @Test
  public void outOfRange() {
    AtomicReference<Iterator<Integer>> iterator = new AtomicReference<>();
    assertDoesNotThrow(() -> {
      iterator.set(new Range(0, 1, 1).iterator());
      iterator.get().next();
    });
    assertThrows(NoSuchElementException.class, () -> {
      iterator.get().next();
    });
  }
}
