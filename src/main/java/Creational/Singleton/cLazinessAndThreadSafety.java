package Creational.Singleton;

// where previously we used the private static final with getInstance()
// to get our singleton instance, to create the instance right away,
// we can do this another way as below, to create the instance only at the point
// when we try to retrieve the instance

// however this is dangerous in case it is being used twice;
// one is creating the instance while the other is checking if it's null...
// so to make it "thread safe" we introduce "synchronisation"

public class cLazinessAndThreadSafety {
    public static void main(String[] args) {

    }
}


class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("Initializing lazy singleton");
    }

    public static /**/ synchronized /**/ LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    // ^^ this method is not thread-proof.  so we can do double-checked locking [deprecated]

    // here we check if it's already been created; if not -
    // then we synchronise the class, and if it's still null, then
    // we create the instance

    public static LazySingleton getInstance2() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}