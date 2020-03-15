package Structural.Proxy;

// not 100% proxy...
// if we wanted to track changes to our Creature's agility value, we can use getters and setters,
// but we can still update it directly as per the constructor.
// so, what we can use is a property proxy so that the property itself becomes an object that performs
// the tracking for itself

import java.util.Objects;

public class cPropertyProxy {
    public static void main(String[] args) {
        Property<Creature> creatureProperty = new Property<>(new Creature(9));
        Creature creature = new Creature(8);

        Property<Integer> x = new Property<>(5); // << you can never do Property<T> a = object.  you MUST use the "new Property..."

        Creature2 creatureProp2 = new Creature2();
        creatureProp2.setAgility(6);
    }
}

class Property<T> { // << this is our proxy property, T being whatever the property is.
    // already at this point we have no way to update "value" directly any more (we can only assign via constructor; "private T vale = ..." = error)
    private T value;

    public Property(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        System.out.println("Logging: setting value to " + value);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property<?> property = (Property<?>) o;
        return Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

class Creature2 {
    private Property<Integer> agility = new Property<>(5);

    public void setAgility(int agilityVal){
        this.agility.setValue(agilityVal);
    }
    public int getAgility(){
        return this.agility.getValue();
    }

}


class Creature {
    private int agility;

    public Creature(int agility) {
        this.agility = 123;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }
}