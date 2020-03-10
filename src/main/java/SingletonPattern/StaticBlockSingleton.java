package SingletonPattern;

// what if you have exceptions inside the singleton constructor?
// you can overcome this by using a static block

import java.io.File;
import java.io.IOException;

public class StaticBlockSingleton {

    public static void main(String[] args) {
        StaticBlockSi instance = StaticBlockSi.getInstance();
        System.out.println(instance);
    }
}

class StaticBlockSi {
    private StaticBlockSi() throws IOException {
        System.out.println("Singleton is initializing!");
        File.createTempFile(".", "."); // << invalid! exception thrown...
    }

    private static StaticBlockSi instance;
    // ^^ we can no longer have a static final and initialize directly...
    // but still "static", and initialized as so:

    static {  // << although this block is not inside any method, it works fine
        // because it's static, it runs when we try getInstance() and effectively
        // behaves like a constructor; but it handles our exception
        try {
            instance = new StaticBlockSi();
        }
        catch (Exception e) {
            System.err.println("ERROR, new exception");
        }
    }

    public static StaticBlockSi getInstance(){
        return instance;
    }

}