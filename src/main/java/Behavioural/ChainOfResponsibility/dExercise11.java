package Behavioural.ChainOfResponsibility;

import java.util.ArrayList;
import java.util.List;

public class dExercise11 {
    public static void main(String[] args) {
        Game game = new Game();
        Goblin goblin = new Goblin(game);
        Goblin goblin1 = new Goblin(game);
        Goblin goblin2 = new Goblin(game);
        Goblin goblin3 = new Goblin(game);
        GoblinKing goblinKing = new GoblinKing(game);

        System.out.println("Goblin 2: " + goblin1.getAttack());
        System.out.println("Goblin 5: " + goblin1.getDefense());

        System.out.println("G King: " + goblinKing.getAttack());


    }
}


abstract class Creature2
{
    public abstract int getAttack();
    public abstract int getDefense();
}

class Goblin extends Creature2
{
    Game game;
    int attack, defense;

    public Goblin(Game game)
    {
        this.attack = this.defense = 1;
        this.game = game;
//        if(!game.creatures.contains(this))
//            game.creatures.add(this);
    }

    @Override
    public int getAttack() {

            for(Creature2 bufff : game.creatures) {
                if(bufff instanceof GoblinKing && !(this instanceof GoblinKing)) {
                    System.out.println("Goblin King is in play! Extra attack strength spurs you forward");
                    return attack + 1;  // temporary effect i think, if the king dies the attack value should return to normal
                }
        }
        return attack;
    }

    @Override
    public int getDefense()
    {
        int allies = game.creatures.size() - 1; // << can't count yourself!
        if(allies > 0) {
            System.out.println(String.format("Your %d comrades shield each other effectively; defense boosted.", allies));
            return defense + allies;
        }
        return defense;
    }
}

class GoblinKing extends Goblin
{

    public GoblinKing(Game game)
    {
        super(game);
        this.attack = this.defense = 3;
    }
}

enum Statistic
{
    ATTACK, DEFENSE
}

class Game
{
    public List<Creature2> creatures = new ArrayList<>();
}
