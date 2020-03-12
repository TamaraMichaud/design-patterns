package Creational.Prototype;

// when it's easier to copy an existing object than to fully initialize a new one

// an existing (partially or fully constructed) design is called a Prototype

// we make a copy (aka clone the prototype) and customise it
// we facilitate this process by implementing a factory

// DO NOT USE CLONEABLE.  it's clone() method is simply return super.clone(); -> a shallow clone.
// for a deep clone you'd need to override this method all over town and it gives no indication of wtf it's really doing.

// so. Copy Constructors.

// see end for summary notes

public class aPrototype {
    public static void main(String[] args) {
        Employee john = new Employee("John", new Address("123 some street", "johnstown", "usa"));
        Employee jane = new Employee(john);
        jane.name = "Jane";
        jane.address.country = "uk";

        System.out.println(john);
        System.out.println(jane);
    }
}

@SuppressWarnings("WeakerAccess")
class Address {
    public String streetAddress, city, country;

    public Address(String streetAddress, String city, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

    public Address(Address other) {
        this(other.streetAddress, other.city, other.country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

@SuppressWarnings({"WeakerAccess", "unused"})
class Employee {
    public String name;
    public Address address;

    public Employee(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Employee(Employee other) {
        this.name = other.name;
        this.address = new Address(other.address);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}


// how to handle prototypes:
// - to implement a prototype, create or part-create your initial object and store it somewhere
// - to then clone that prototype;
    // implement your own deep copy functionality; you can either copy all involved constructors if possible or (ugly) implement cloneable perhaps
    // or serialize and deserialize ** DO IT THIS WAY ** using any of many libraries
// - then you can customize your clone as you wish

