package SingletonPattern;

import java.util.function.Supplier;

// singletons summary
// making a 'safe' singleton is easy; construct a static Lazy<T> and return it's value
// make your singleton an implementation of an abstract class for testability
// consider defining singleton lifetime in dependency injection container (spring stuff i think)

public class fExercise {}

class SingletonTester {
    public static boolean isSingleton(Supplier<Object> func) {
        // .equals() -> same type.
        // ==        -> same object.
        return func.get() == func.get();
    }
}

