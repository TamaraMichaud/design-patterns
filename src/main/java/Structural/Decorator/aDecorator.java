package Structural.Decorator;

// augment an object with additional functionality
// without changing the object itself (clashes with open/close and possibly single-responsibility if the new function should be separate)
// need to be able to interact with existing structures
// two options:
//      inherit from required object if possible; some classes are final - e.g. String
//      build a decorator. which simply references the decorated objects

// decorator pattern: facilitates the addition of behaviours into individual objects without inheriting from them

// by using a common interface you can make your decorator behave as the underlying object; that object being a
// private member of the decorator

// but note that we have some issues here. for example in c, greenClearCircle is not really a circle, we cannot use "resize()"...


public class aDecorator {
}
