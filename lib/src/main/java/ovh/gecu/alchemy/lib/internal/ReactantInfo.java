package ovh.gecu.alchemy.lib.internal;

public class ReactantInfo {
  public final Class<?> type;
  public final Object value;

  public ReactantInfo(Object element) {
    this.type = element.getClass();
    this.value = element;
  }

  public ReactantInfo(Class<?> countedElement) {
    this.type = countedElement;
    this.value = null;
  }

  public Object getStorageValue() {
    if (this.value == null) {
      return this.type;
    }
    return this.value;
  }
}
