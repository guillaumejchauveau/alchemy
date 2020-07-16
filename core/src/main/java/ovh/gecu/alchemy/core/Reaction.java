package ovh.gecu.alchemy.core;

import java.util.function.BiFunction;

/**
 * A function processing two elements of known types (reactants) into a set of
 * elements (products), or null if the two reactants cannot react according to
 * logic specific to the reaction.
 *
 * The reactant can be instance or quantity stored. They can also be of the same
 * type but one is instance-stored and not the other. Quantity-stored elements
 * are passed as null parameters.
 *
 * @param <R1> The type of the first reactant
 * @param <R2> The type of the second reactant
 */
@FunctionalInterface
public interface Reaction<R1, R2> extends BiFunction<R1, R2, Object[]> {
}
