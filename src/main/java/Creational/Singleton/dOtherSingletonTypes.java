package Creational.Singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class dOtherSingletonTypes {
    public static void main(String[] args) {
        // example of multiton
        Printer.get(Subsystem.AUX);
        Printer.get(Subsystem.PRIMARY);
        Printer.get(Subsystem.AUX);
        Printer.get(Subsystem.FALLBACK); // multiple singletons... AUX only gets created once no matter how many calls. (same for all)
    }
}

/* INNER STATIC SINGLETON -------------------------------------------- */

class InnerStaticSingleton {

    private InnerStaticSingleton() {
    } // regular class

    // nested inner class; has access to private member (constructor ^^) of outer class
    private static class Impl {
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
    }

    public InnerStaticSingleton getInstance() {
        return Impl.INSTANCE;
    }

}


/* MULTITON PATTERN ---------------------------------------------- */
// like singleton for multiple cases... seems counter-intuitive but would be for a
// finite set of instances; for each you can implement different set of params
enum Subsystem {
    PRIMARY,
    AUX,
    FALLBACK
}

class Printer { // << our multiton. we want only ONE printer, for EACH SubSystem...
    private static int instanceCount = 0;
    private Printer(){
        instanceCount++;
        System.out.println(String.format("A total of %d instances created so far", instanceCount));
    }

    private static HashMap<Subsystem, Printer> instances = new HashMap<>();

    public static Printer get(Subsystem ss) {
        if(instances.containsKey(ss)) {
            return instances.get(ss);
        }

        Printer instance = new Printer(); // << only if it's the first time for that Subsystem value
        instances.put(ss, instance);
        return instance;

    }
}









/* MONOSTATE PATTERN -------------------------------------------- */
//pernicious: adj; having a harmful effect, especially in a gradual or subtle way
// unintuitive... skipping these dirty ones since i dont need extra confusion!!



/* ENUM BASED SINGLETON ------- [ ngh, i do not understand and it's crap anyway ] --------------------------------- */

enum EnumBasedSingleton {  // << serializable by default.  but cannot inherit from this...
    // << has PRIVATE constructor by default. but you can still create another
    // - also PRIVATE... it's an enum... too odd
    // BUT -> when serializing an enum, fields are not included, only INSTANCE.

    INSTANCE;

    EnumBasedSingleton() {  // << constructor. doesn't need any access modifier: it's private and that's all you can have.
        value = 42;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public int setValue(int value) {
        return this.value = value;
    }
}

class example {

    public static void main(String[] args) throws Exception {
        String filename = "testFile.txt";
        EnumBasedSingleton singleton1 = EnumBasedSingleton.INSTANCE;
        singleton1.setValue(111);
        saveToFile(singleton1, filename);
        // -------------- //
//        ^^ copied into a file. now we read it back out again:
        EnumBasedSingleton singleton2 = readFromFile(filename);
        singleton2.setValue(653);

        System.out.println(singleton1.getValue());
        System.out.println(singleton2.getValue());

    }

    static void saveToFile(EnumBasedSingleton singleton, String filename) throws Exception {
        try (FileOutputStream fileout = new FileOutputStream(filename);
             ObjectOutputStream outputstream = new ObjectOutputStream(fileout)) {
            outputstream.writeObject(singleton);
        }
    }

    static EnumBasedSingleton readFromFile(String filename) throws Exception {
        try (FileInputStream filein = new FileInputStream(filename);
             ObjectInputStream inputStream = new ObjectInputStream(filein)) {
            return (EnumBasedSingleton) inputStream.readObject();
        }
    }
}
