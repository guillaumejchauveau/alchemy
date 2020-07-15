package ovh.gecu.alchemy.lib.annotation;

import java.lang.annotation.*;

/**
 * Annotates a method as a reaction for a {@link ovh.gecu.alchemy.lib.ProgramFactory}.
 * The factory will use the method to create a {@link ovh.gecu.alchemy.lib.internal.MethodReaction}
 * when the class with the annotated method is "loaded".
 *
 * @see ovh.gecu.alchemy.lib.ProgramFactory#load(Class)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Reaction {
}
