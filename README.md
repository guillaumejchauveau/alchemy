# Alchemy ![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/guillaumejchauveau/alchemy/CI/master?style=flat-square) ![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/guillaumejchauveau/alchemy?include_prereleases&style=flat-square)

## Introduction
Alchemy is a computational model inspired of chemical reactions. It consists of
elements, a typed unit of data, and rules (or reactions) that describe how two
elements interact with each other, given their types. An Alchemy program, or
cell, contains a set of elements which represents the program's state, and a set
of reactions.

The program is executed by a reactor, that tries to perform reactions until
certain conditions are met. This process consists of selecting two random
elements (reactants) and find a reaction corresponding to their types. If it
exists, then the reactants are replaced by the products of the reaction. The end
result of a program is the elements left in the cell.

Elements can be stored in a cell in two different fashion. The first is instance
storage, where each element is stored individually, and so is able to hold
specific data. For example, 1 and 5 are two elements of type integer, with their
own value. The second is quantity storage, here only the quantity of elements of
a given type is stored. For example, lets have two elements types A and B. With
quantity storage, we can say that the cell contains 10 elements of type A and 3
of type B. It is possible to store elements of the same type in both modes,
however the instances are not counted in the quantity storage.

Alchemy is divided in two parts, core and lib. The core defines the interfaces
of the cell, reaction and reactor. They are to be used to create interchangeable
implementations. Lib is a Java implementation of Alchemy.

## Installation
Alchemy requires Java 11 or newer. Lib uses [Speedment tuples](https://github.com/speedment/speedment/tree/master/common-parent/tuple),
[Log4j](https://logging.apache.org/log4j/2.x/) and
[Jansi](https://fusesource.github.io/jansi/).

This project supports [JitPack](https://jitpack.io/#guillaumejchauveau/alchemy).
Alternatively, you can download an archive with all the necessary JARs on
[GitHub releases](https://github.com/guillaumejchauveau/alchemy/releases).

## Usage (Lib)

### Configuration
Lib must be initialized in order to configure the internal logging framework
(Log4j), using the `Alchemy` facade.
```java
import ovh.gecu.alchemy.lib.Alchemy;

...

Alchemy.configure(true); // true for detailed logging.
```

### Creating a program
Lib uses program factories to easily create programs. You can create a factory
for a program with `Alchemy#newProgram()`. The `ProgramFactory` interface
exposes various method to add elements and reactions to the cell, as well as
associating a reactor to the cell, running the program and retrieving the
result.
```java
import ovh.gecu.alchemy.lib.Alchemy;
import ovh.gecu.alchemy.lib.ProgramFactory;
import myElements.A; // Class A defines a custom type of element.

import java.util.Arrays;

...

Alchemy.configure(true);
ProgramFactory myProgram = Alchemy.newProgram();
// Adds integer element '1' to the cell. You can also use iterators, iterables
// and streams.
myProgram.add(1);
// Types (java.lang.Class instances) are quantity stored.
myProgram.add(A.class);
// Adds 5 to the quantity of elements of type A.
myProgram.add(A.class, 5);
// Iterable of elements.
myProgram.add(Arrays.asList(3, 4, 'a', 'b', 'c'));

// Adds a reaction for two integer elements that produces one element with the
// sum.
myProgram.add(Integer.class, Integer.class, (a, b) -> new Object[]{a + b});
// Since the elements of type A are quantity-stored, a1 and a2 are null when the
// reaction is invoked.
// This reaction does not have any products.
myProgram.add(A.class, A.class, (a1, a2) -> new Object[0]);
```

Lib provides a convenient way of defining reactions using the public methods of
a class. You just need to annotate the method with `Reaction` and use
`ProgramFactory#load`. Refer to the Javadoc for more details.
```java
package myElements;

import ovh.gecu.alchemy.lib.annotation.Reaction;

class A {
  @Reaction
  public static Object[] myReaction(A a1, A a2) {
    return new Object[0];
  }
}
```

You need to associate a reactor to the cell in order to run the program. Lib
provides `BasicReactor`, a simple implementation that only needs the number of
time it will try to perform reactions.

```java
import ovh.gecu.alchemy.lib.BasicReactor;

...

// The reactor will try to perform 500 reactions before stopping.
myProgram.withReactor(new BasicReactor(500));
// Runs the program with the reactor.
myProgram.run();
```

In order to retrieve the result of the computation, access the cell using
`ProgramFactory#getCell()`. The cell lets you access the instance-stored
elements with `Cell#getInstanceElements()` and the quantity-stored with
`Cell#getQuantityElements()`. They respectively return a collection of objects
and a map between types and integers (the quantity associated with a type).

Examples are available in [this folder](https://github.com/guillaumejchauveau/alchemy/tree/master/lib/src/integrationTest/java/ovh/gecu/alchemy/lib/integrationTest).
