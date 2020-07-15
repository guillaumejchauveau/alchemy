package ovh.gecu.alchemy.core;

import java.util.function.BiFunction;

/**
 * A function processing two elements of known types (reactants) into a set of
 * elements (products).
 * @param <R1> The type of the first reactant
 * @param <R2> The type of the second reactant
 */
@FunctionalInterface
public interface Reaction<R1, R2> extends BiFunction<R1, R2, Object[]> {
}
