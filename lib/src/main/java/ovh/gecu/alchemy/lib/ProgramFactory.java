package ovh.gecu.alchemy.lib;

import ovh.gecu.alchemy.core.Cell;
import ovh.gecu.alchemy.core.Reaction;
import ovh.gecu.alchemy.core.Reactor;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.stream.Stream;

public interface ProgramFactory {
  Cell getCell();

  Reactor getReactor();

  ProgramFactory load(Method method);

  ProgramFactory load(Class<?> clazz);

  <R1, R2> ProgramFactory add(Class<R1> reactantType1, Class<R2> reactantType2, Reaction<R1, R2> reaction);

  ProgramFactory add(Class<?> element, Integer count);

  ProgramFactory add(Iterator<?> elementIterator);

  ProgramFactory add(Iterable<?> elementIterable);

  ProgramFactory add(Stream<?> elementStream);

  ProgramFactory withReactor(Reactor reactor);

  default ProgramFactory run() {
    this.getReactor().run();
    return this;
  }
}
