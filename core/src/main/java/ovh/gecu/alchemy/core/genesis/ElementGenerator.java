package ovh.gecu.alchemy.core.genesis;

import ovh.gecu.alchemy.core.solution.Element;

import java.util.List;

/**
 * Generates a list of elements.
 */
@FunctionalInterface
public interface ElementGenerator {
  List<Element> generate();
}
