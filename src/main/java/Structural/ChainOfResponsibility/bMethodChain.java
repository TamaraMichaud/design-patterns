package Structural.ChainOfResponsibility;

// most popular pattern for chainOfResponsibility

public class bMethodChain {
    public static void main(String[] args) {
        Creature goblin = new Creature("Goblin", 2, 2);
        System.out.println(goblin);
        // instantiate the root class!! CreatureModifier...
        // why? it doesn't do anything for itself?
        // because we are chaining methods; we want a base obj on which we can chain these t hings...
        CreatureModifier root = new CreatureModifier(goblin);

        NoBonusesModifier noBonusesModifier = new NoBonusesModifier(goblin);
        root.add(noBonusesModifier);

        DoubleAttackValueModifier passThatGoblin = new DoubleAttackValueModifier(goblin);
        root.add(passThatGoblin);
        System.out.println("Doubled the attack value?");

        root.add(new IncreaseDefenseValModifier(goblin));
        System.out.println("increasing defense value...");
        // wtf is this?? it looks like sht

        root.handle(); // gotta do this or it wont work!

        // how to disrupt ch=of=resp or cancel it entirely?
        // see "NoBonusesModifier" class... which we'd need to call at the start of this in order for it to work...
        // you can find it commented-out above


    }
}

class NoBonusesModifier extends CreatureModifier {

    public NoBonusesModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("No bonuses for you");
        // no call to super. any bonuses coming after this one wont be applied because we have disrupted the chain of command.
    }
}

class Creature {
    public String name;
    public int attack, defense;

    public Creature(String name, int attack, int defense) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}

class CreatureModifier { // << should be chainable... as such the same creature needs to be treated
    protected Creature creature;
    protected CreatureModifier next;

    public CreatureModifier(Creature creature) {
        this.creature = creature;
    }

    public void add(CreatureModifier nextModifier) {
        if (next == null) {
            this.next = nextModifier;
        } else {
            next.add(nextModifier); // << potentially indefinite chain
        }
    }

    public void handle() {
        if (next != null)
            next.handle();
    }
}

class DoubleAttackValueModifier extends CreatureModifier {

    public DoubleAttackValueModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        creature.attack *= 3; // double the value as promised in the class name
        System.out.println(creature.name + "'s attack level has been DOUBLED to: " + creature.attack);
        super.handle(); // << chain of responsibility
    }
}

class IncreaseDefenseValModifier extends CreatureModifier {

    public IncreaseDefenseValModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        creature.defense += 3;
        System.out.println(creature.name + "'s defense level has increased to: " + creature.attack);
        super.handle();
    }
}