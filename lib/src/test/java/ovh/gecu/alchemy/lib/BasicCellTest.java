package ovh.gecu.alchemy.lib;

import com.speedment.common.tuple.Tuples;
import org.junit.jupiter.api.Test;
import ovh.gecu.alchemy.core.Reaction;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicCellTest {
  static class A {

  }

  static class B {

  }

  @Test
  @SuppressWarnings("unchecked")
  public void reactionDefinition() {
    var cell = new BasicCell();
    Reaction<?, ?> reaction = (A a, B b) -> new Object[0];
    cell.addReactionDefinition(A.class, B.class, (Reaction<Object, Object>) reaction);

    assertTrue(cell.reactionDefinitions.containsKey(Tuples.of(A.class, B.class)));
    assertFalse(cell.reactionDefinitions.containsKey(Tuples.of(B.class, A.class)));
    assertEquals(reaction, cell.reactionDefinitions.get(Tuples.of(A.class, B.class)));
    assertThrows(IllegalArgumentException.class, () -> {
      cell.addReactionDefinition(A.class, B.class, (Reaction<Object, Object>) reaction);
    }, "Cell should not accept two reactions with the same reactants");

    Reaction<?, ?> symmetricReaction = (B b, A a) -> new Object[0];
    assertDoesNotThrow(() -> {
      cell.addReactionDefinition(B.class, A.class, (Reaction<Object, Object>) symmetricReaction);
    }, "Cell should accept the opposite reaction");
    assertTrue(cell.reactionDefinitions.containsKey(Tuples.of(B.class, A.class)));
    assertEquals(symmetricReaction, cell.reactionDefinitions.get(Tuples.of(B.class, A.class)));
  }

  @Test
  public void elementStorage() {
    var cell = new BasicCell();
    cell.addQuantityElement(A.class, 1);
    assertTrue(cell.quantityElements.containsKey(A.class));
    assertEquals(1, cell.quantityElements.get(A.class));
    cell.addQuantityElement(A.class, 1);
    assertEquals(2, cell.quantityElements.get(A.class));
    cell.addElement(B.class);
    assertTrue(cell.quantityElements.containsKey(B.class));
    assertEquals(1, cell.quantityElements.get(B.class));

    var element = "element";
    cell.addElement(element);
    assertTrue(cell.instanceElements.contains(element));

    assertEquals(4, cell.getTotalElementCount());
  }

  @Test
  public void reactants() {
    var cell = new BasicCell();
    assertNull(cell.pickReactants());
    var element1 = "element1";
    cell.addElement(element1);
    assertNull(cell.pickReactants());
    var element2 = "element2";
    cell.addElement(element2);
    var reactants = cell.pickReactants();
    assertNotNull(reactants);
    List<Object> possibleReactants = Arrays.asList(element1, element2);
    assertTrue(possibleReactants.contains(reactants.get0().getStorageValue()));
    assertTrue(possibleReactants.contains(reactants.get1().getStorageValue()));
    assertEquals(0, cell.getTotalElementCount());

    cell.addElement(A.class);
    assertNull(cell.pickReactants());
    cell.addElement(B.class);
    var reactants2 = cell.pickReactants();
    assertNotNull(reactants2);
    List<Object> possibleReactants2 = Arrays.asList(A.class, B.class);
    assertTrue(possibleReactants2.contains(reactants2.get0().getStorageValue()));
    assertTrue(possibleReactants2.contains(reactants2.get1().getStorageValue()));
    assertEquals(0, cell.getTotalElementCount());

    cell.addElement(element1);
    cell.addElement(A.class);
    var reactants3 = cell.pickReactants();
    assertNotNull(reactants3);
    List<Object> possibleReactants3 = Arrays.asList(A.class, element1);
    assertTrue(possibleReactants3.contains(reactants3.get0().getStorageValue()));
    assertTrue(possibleReactants3.contains(reactants3.get1().getStorageValue()));
    assertEquals(0, cell.getTotalElementCount());
  }

  static class ReactionMock implements Reaction<A, B> {
    public A lastA;
    public B lastB;

    @Override
    public Object[] apply(A a, B b) {
      this.lastA = a;
      this.lastB = b;
      return new Object[0];
    }
  }

  static class ReactionMock2 implements Reaction<A, A> {
    public A lastA1;
    public A lastA2;

    @Override
    public Object[] apply(A a1, A a2) {
      this.lastA1 = a1;
      this.lastA2 = a2;
      return new Object[0];
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void products() {
    var cell = new BasicCell();
    assertNull(cell.getProducts(
      Tuples.of(new ReactantInfo("1"), new ReactantInfo("2"))
    ));

    Reaction<?, ?> reaction = new ReactionMock();
    cell.addReactionDefinition(A.class, B.class, (Reaction<Object, Object>) reaction);

    var a1 = new A();
    var b1 = new B();
    assertNotNull(cell.getProducts(
      Tuples.of(new ReactantInfo(a1), new ReactantInfo(b1))
    ));
    assertEquals(a1, ((ReactionMock) reaction).lastA);
    assertEquals(b1, ((ReactionMock) reaction).lastB);

    var a2 = new A();
    var b2 = new B();
    assertNotNull(cell.getProducts(
      Tuples.of(new ReactantInfo(b2), new ReactantInfo(a2))
    ), "Cell should find symmetric reaction");
    assertEquals(a2, ((ReactionMock) reaction).lastA);
    assertEquals(b2, ((ReactionMock) reaction).lastB);

    assertNotNull(cell.getProducts(
      Tuples.of(new ReactantInfo(A.class), new ReactantInfo(B.class))
    ));
    assertNull(((ReactionMock) reaction).lastA);
    assertNull(((ReactionMock) reaction).lastB);

    var b3 = new B();
    assertNotNull(cell.getProducts(
      Tuples.of(new ReactantInfo(A.class), new ReactantInfo(b3))
    ));
    assertNull(((ReactionMock) reaction).lastA);
    assertEquals(b3, ((ReactionMock) reaction).lastB);

    Reaction<?, ?> reaction2 = new ReactionMock2();
    cell.addReactionDefinition(A.class, A.class, (Reaction<Object, Object>) reaction2);


    var a4 = new A();
    var a5 = new A();
    assertNotNull(cell.getProducts(
      Tuples.of(new ReactantInfo(a4), new ReactantInfo(a5))
    ));
    assertEquals(a4, ((ReactionMock2) reaction2).lastA1);
    assertEquals(a5, ((ReactionMock2) reaction2).lastA2);

    assertNotNull(cell.getProducts(
      Tuples.of(new ReactantInfo(a5), new ReactantInfo(a4))
    ));
    assertEquals(a5, ((ReactionMock2) reaction2).lastA1);
    assertEquals(a4, ((ReactionMock2) reaction2).lastA2);

    var a6 = new A();
    assertNotNull(cell.getProducts(
      Tuples.of(new ReactantInfo(a6), new ReactantInfo(A.class))
    ));
    assertEquals(a6, ((ReactionMock2) reaction2).lastA1);
    assertNull(((ReactionMock2) reaction2).lastA2);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void react() {
    var cell = new BasicCell();

    assertFalse(cell.react());

    var r1 = new A();
    var r2 = new B();
    cell.addElement(r1);
    cell.addElement(r2);
    assertFalse(cell.react());
    assertEquals(2, cell.instanceElements.size());
    assertTrue(cell.instanceElements.contains(r1));
    assertTrue(cell.instanceElements.contains(r2));

    var p1 = "product";
    Reaction<?, ?> reaction = (A a, B b) -> new Object[]{p1};
    cell.addReactionDefinition(A.class, B.class, (Reaction<Object, Object>) reaction);
    assertTrue(cell.react());
    assertEquals(1, cell.instanceElements.size());
    assertTrue(cell.instanceElements.contains(p1));
  }
}
