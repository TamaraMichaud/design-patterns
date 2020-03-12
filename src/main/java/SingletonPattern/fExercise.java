package SingletonPattern;

import java.util.function.Supplier;

public class fExercise {}

class SingletonTester {
    public static boolean isSingleton(Supplier<Object> func) {
        // .equals() -> same type.
        // ==        -> same object.
        return func.get() == func.get();
    }
}

