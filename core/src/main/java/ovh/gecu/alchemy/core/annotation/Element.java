package ovh.gecu.alchemy.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Element {
  boolean isEnumerated = false;
}
