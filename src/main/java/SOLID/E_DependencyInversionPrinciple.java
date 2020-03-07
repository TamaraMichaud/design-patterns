package SOLID;

// A. High-level modules should not depend on low-level modules
//   Both should depend on abstractions

// B. Abstractions should not depend on details
//   Details should depend on abstractions

// abstraction = abstract class or interface. signature of something that performs a particular action

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class E_DependencyInversionPrinciple {
    public static void main(String[] args) {
//        System.out.println("hello");
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

//        Relationships relationships = new Relationships(); // bad
        Relationships2 relationships = new Relationships2(); // good

        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research(relationships); // bad
    }
}

// family tree...
enum Relationship {
    PARENT,
    CHILD,
    SIBLING
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

class Relationships { // ** this is a low-level module. it only contains a list, no business logic of its own.
    // single responsibility: allow manipulations of this triplets list

    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet(parent, Relationship.PARENT, child));
        relations.add(new Triplet(child, Relationship.CHILD, parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }
}

class Research { // ** this is a high-level module. as such it should not depend on the above
    // performs operations on low-level constructs
    // so how to do it then...?  Abstractions!! Keep reading below

//    public Research(Relationships relationships) { // << High level modules should not depend on low level modules...!
//        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
//        relations.stream().filter(x -> x.getValue0().name.equals("John") &&
//                x.getValue1() == Relationship.PARENT)
//        .forEach(childName -> System.out.println(String.format("John has a child called %s", childName.getValue2().name)));
//    }

    public Research(RelationshipBrowser browser) { // << they should depend on interfaces
        for (Person child : browser.findAllChildrenOf("John")) {
            System.out.println(String.format("John has a child called %s", child.name));
        }
    }
}

// introducing the abstraction
interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

class Relationships2 implements  RelationshipBrowser { // ** this is a low-level module. it only contains a list, no business logic of its own.
    // single responsibility: allow manipulations of this triplets list

    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet(parent, Relationship.PARENT, child));
        relations.add(new Triplet(child, Relationship.CHILD, parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    @Override
    public List<Person> findAllChildrenOf(String name) { // this is where we should do the search.
        // if this list was to change type, then the logic in Research will no longer work.
        // if the logic is in the same class as the list, then both will need to be addressed
        List<Person> children = new ArrayList<>();
        relations.stream().filter(x -> x.getValue0().name.equals(name) &&
                x.getValue1() == Relationship.PARENT)
                .forEach(childPerson -> children.add(childPerson.getValue2()));
        return children;
    }
}
