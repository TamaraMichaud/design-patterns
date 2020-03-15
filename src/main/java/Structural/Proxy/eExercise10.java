package Structural.Proxy;

public class eExercise10 {
    public static void main(String[] args) {
        Person fool = new Person(21);
        System.out.println(fool.drinkAndDrive());

        ResponsiblePerson responsiblePerson = new ResponsiblePerson(17);

        System.out.println(responsiblePerson.drink());
        System.out.println(responsiblePerson.drive());
        System.out.println(responsiblePerson.drinkAndDrive());
    }
}

class Person
{
    private int age;

    public Person(int age)
    {
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String drink() { return "drinking"; }
    public String drive() { return "driving"; }
    public String drinkAndDrive() { return "driving while drunk"; }
}

class ResponsiblePerson extends Person
{
    public ResponsiblePerson(int age) {
        super(age);
    }

    @Override
    public String drink(){
        if (this.getAge() < 18 ){
            return "too young to drink";
        } else {
            return super.drink();
        }
    }

    @Override
    public String drive(){
        if(this.getAge() < 16 ) {
            return "too young to drive.";
        } else {
            return super.drive();
        }
    }

    @Override
    public String drinkAndDrive(){
        return "dead";
    }
}