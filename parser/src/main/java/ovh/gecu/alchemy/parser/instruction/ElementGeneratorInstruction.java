package ovh.gecu.alchemy.parser.instruction;

import ovh.gecu.alchemy.core.genesis.ElementGenerator;
import ovh.gecu.alchemy.lib.LibraryRegistry;

/**
 * An {@link Instruction} representing an {@link ElementGenerator}.
 */
public class ElementGeneratorInstruction extends Instruction<ElementGenerator> {
  /**
   * Initializes a new instruction for an element generator.
   *
   * @param name The name of the element generator
   */
  public ElementGeneratorInstruction(String name) {
    super(name);
  }

  /**
   * Creates the element generator corresponding to the instruction.
   *
   * @param registry The registry used to create the element generator
   * @return The element generator created
   */
  @Override
  public ElementGenerator create(LibraryRegistry registry) {
    return registry.createElementGenerator(this.name, this.arguments.toArray());
  }
}
