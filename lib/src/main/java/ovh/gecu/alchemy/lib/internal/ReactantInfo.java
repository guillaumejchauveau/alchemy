package ovh.gecu.alchemy.lib.internal;

/**
 * Utility class used internally by BasicCell.
 * Holds the type of a reactant along with its instance if it is a normal
 * element.
 */
public class ReactantInfo {
  /**
   * Type of the reactant.
   */
  public final Class<?> type;
  /**
   * Actual value of the reactant, null if the reactant is a counted element.
   */
  public final Object value;

  public ReactantInfo(Object element) {
    this.type = element.getClass();
    this.value = element;
  }

  public ReactantInfo(Class<?> countedElement) {
    this.type = countedElement;
    this.value = null;
  }

  /**
   * Getter for the actual data stored in the cell.
   *
   * @return The actual instance for a normal element or the type for a counted
   * element
   */
  public Object getStorageValue() {
    if (this.value == null) {
      return this.type;
    }
    return this.value;
  }
}
