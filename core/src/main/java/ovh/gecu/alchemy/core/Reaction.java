package ovh.gecu.alchemy.core;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Reaction<R1, R2> extends BiFunction<R1, R2, Object[]> {
}
