package Creational.PrototypeMaybe;

// what if you can't make copies of all of the constructors, it's a complex heirarchy and/or you dont have access
// we can just use the Apache Commons lib to directly take a copy of our Serializable class
// but there are many ways and libraries using serialisation. or even reflection

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class bSerialization {

    public static void main(String[] args) {
        Foo foo1 = new Foo(5, "adsfsd");
        Foo foo2 = SerializationUtils.roundtrip(foo1);// << serializes and then de-s.zs it -> which thereby creates a brand new copy. deep copy

        foo2.stuff = 500;

        System.out.println(foo1);
        System.out.println(foo2);
    }
}


@SuppressWarnings("WeakerAccess")
class Foo implements Serializable {
    public int stuff;
    public String whatever;

    public Foo(int stuff, String whatever) {
        this.stuff = stuff;
        this.whatever = whatever;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "stuff=" + stuff +
                ", whatever='" + whatever + '\'' +
                '}';
    }
}