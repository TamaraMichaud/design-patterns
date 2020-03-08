package Creational;

// Fluent Builder Inheritance with Recursive Generics
public class FluentBuilder {

    public static void main(String[] args) {
        PersonBuilder pb = new PersonBuilder();
        Person john = pb.withName("John")
//                .worksAt // << doesn't work. because withName returns a PersonBuilder, NOT an EmployeeBuilder...
                .build();

        EmployeeBuilderRG pbrg = new EmployeeBuilderRG();
        Person jane = pbrg.withName("Jane")
                .worksAt("Ffs")
                .build();

        System.out.println(jane);
    }
}

class Person {
    public String name;
    public String occupation;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}

class EmployeeBuilder extends PersonBuilder {
    public EmployeeBuilder worksAt(String position) {
        person.occupation = position;
        return this; // << this looks ok but personbuilder only returns personbuilders, not employeebuilders...
    }
}

class PersonBuilder {
    protected Person person = new Person();

    public PersonBuilder withName(String name) {
        person.name = name;
        return this;
    }

    public Person build() {
        return person;
    }
}


class PersonBuilderRecursiveGenerics<TAM extends PersonBuilderRecursiveGenerics<TAM>> {
    protected Person person = new Person();

    public TAM withName(String name) {
        person.name = name;
//        return (TAM) this;
        return self();
    }

    public Person build() {
        return person;
    }

    public TAM self() {
        return (TAM) this;
    }
}


class EmployeeBuilderRG extends PersonBuilderRecursiveGenerics<EmployeeBuilderRG> {
    public EmployeeBuilderRG worksAt(String position) {
        person.occupation = position;
        return self();
    }

    @Override
    public EmployeeBuilderRG self() {
        return this;
    }
}