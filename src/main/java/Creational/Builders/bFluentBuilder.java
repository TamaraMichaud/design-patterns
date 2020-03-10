package Creational.Builders;

// Fluent Builder Inheritance with Recursive Generics

// for a fluent interface to propagate across inheritance heirarchies,
// then you need to implement recursive generics, this idea of always
// returning the type reference for the lowest derived type that you are working with.
// instead of just personbuilder, you make personbuilder taht takes a type argument of itself and is an extension of itself
// and then make any inheritor extend personbuilder as the type argument



@SuppressWarnings("unused")
public class bFluentBuilder {

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

@SuppressWarnings("WeakerAccess")
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

@SuppressWarnings("unused")
class EmployeeBuilder extends PersonBuilder {
    public EmployeeBuilder worksAt(String position) {
        person.occupation = position;
        return this; // << this looks ok but personbuilder only returns personbuilders, not employeebuilders...
    }
}

@SuppressWarnings("WeakerAccess")
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


@SuppressWarnings({"WeakerAccess", "unchecked"})
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


@SuppressWarnings("WeakerAccess")
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