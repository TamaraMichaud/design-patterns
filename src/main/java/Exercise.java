public class Exercise {
    public static void main(String[] args) {
        System.out.println("personfactory should increment the person id each time it creates one");
        PersonFactory pf = new PersonFactory();
        pf.createPerson("john");
        pf.createPerson("jack");
        pf.createPerson("dave");

    }
}

class Person
{
    public int id;
    public String name;

    public Person(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
}

class PersonFactory
{
    private int counter;

    public PersonFactory() {
        this.counter = 0;
    }

    public Person createPerson(String name)
    {
        return new Person(counter++, name);
    }
}