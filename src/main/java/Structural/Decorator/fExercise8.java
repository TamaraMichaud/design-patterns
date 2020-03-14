package Structural.Decorator;

// a Dragon is both a Bird and a Lizard. Complete the Dragon's interface. Beware when implementing setAge()

public class fExercise8 {

    public static void main(String[] args) {
        Dragon dragon = new Dragon();
        dragon.setAge(0);
        System.out.println(dragon.fly());
        System.out.println(dragon.crawl());
    }
}

class Bird
{
    public int age;

    public String fly()
    {
        return age < 10 ? "flying" : "too old";
    }
}

class Lizard
{
    public int age;

    public String crawl()
    {
        return (age > 1) ? "crawling" : "too young";
    }
}

class Dragon
{
    private Bird bird = new Bird();
    private Lizard lizard = new Lizard();

    public void setAge(int age)
    {
        this.bird.age = this.lizard.age = age;
    }
    public String fly()
    {
     return "You want me to fly? I am " + this.bird.fly();
    }
    public String crawl()
    {
        return "You want me to crawl? I am " + this.lizard.crawl();
    }
}