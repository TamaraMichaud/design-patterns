package SingletonPattern;

// stop an object from being created more than once in a project.
// simple enough to do, but some basic issues to consider:
// 1. reflection: if you really want to get to this private constructor, reflection will allow it
// 2. serialisation: you can print the object to a file and read it back again -> new deep copy created...

// -- create your class as "implements Serializable"
// -- AND implement the following method to ensure that any in/out streams return THE instance, not a new one
//       protected Object readResolve(){ return INSTANCE; }
// ... although this is super confusing: then we're not reading the file at all...





import java.io.*;

public class aBasicSingleton {
    public static void main(String[] args) throws Exception {

        BasicSingletonEx singleton1 = BasicSingletonEx.getInstance();
        singleton1.setValue(65432);

        saveToFile(singleton1, "./singletonEx1.out");
        // ^^ java.io.NotSerializableException: {{classname}} << ass "implements Serializable" to that class

        singleton1.setValue(123546);

        BasicSingletonEx singleton2 = readFromFile("./singletonEx1.out");
        // ^^ by having the "readResolve()" method in our serializable class, this stream resolver now returns our INSTANCE variable rather than a new object
        // (although then we're not reading from the file at all...)

        System.out.println(singleton1.getValue());
        System.out.println(singleton2.getValue());  // << ^^ two different numbers! not ok
    }

    static void saveToFile(BasicSingletonEx singletonObj, String fileName) throws Exception {

        try(
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream) ) {

            objectOutputStream.writeObject(singletonObj);
        }
    }

    static BasicSingletonEx readFromFile(String fileName) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        return (BasicSingletonEx) objectInputStream.readObject();
    }
}

class BasicSingletonEx implements Serializable {
    public int value;
    private BasicSingletonEx(){} // << private, only this class can access its own constructor... mostly

    public static final BasicSingletonEx INSTANCE = new BasicSingletonEx();

    public static BasicSingletonEx getInstance(){
        return INSTANCE;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }

    protected Object readResolve(){
        return INSTANCE;
    }
}