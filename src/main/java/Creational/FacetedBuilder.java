package Creational;

// multiple builders working in tandem

public class FacetedBuilder {

    public static void main(String[] args) {
        Human someDude = new HumanBuilder()
                .lives()
                    .onStreet("123 street name")
                    .postCode("W15 90D")
                .works()
                    .at("The Shop")
                    .earns(99999)
                .build();

        System.out.println(someDude);
    }
}

class Human {
    // addresss
    public String streetAddress, postCode, city;
    // work
    public String company, position;
    public int income;

    @Override
    public String toString() {
        return "Human{" +
                "streetAddress='" + streetAddress + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", income=" + income +
                '}';
    }
}

// builder facade
class HumanBuilder {
    protected Human human = new Human();

    public AddressBuilder lives(){
        return new AddressBuilder(human);
    }

    public JobBuilder works(){
        return new JobBuilder(human);
    }

    public Human build(){
        return human;
    }
}

class AddressBuilder extends HumanBuilder {
    public AddressBuilder(Human human) {
        this.human = human; // why don't we need this as a class variable to access it...?
        // and how does this one work when the last one didnt?!
        // ^^ because of Human.works() and Human.lives() methods...
    }

    public AddressBuilder onStreet(String streetAddress) {
        this.human.streetAddress = streetAddress;
        return this;
    }

    public AddressBuilder postCode(String postCode) {
        this.human.postCode = postCode;
        return this;
    }
}

class JobBuilder extends HumanBuilder {

    public JobBuilder(Human human) {
        this.human = human; // why don't we need this as a class variable to access it...?  // and how does this one work when the last one didnt?!
    }

    public JobBuilder at(String company) {
        this.human.company = company;
        return this;
    }

    public JobBuilder earns(int salary) {
        this.human.income = salary;
        return this;
    }

}