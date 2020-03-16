package Behavioural.Command;

public class bExercise {
    public static void main(String[] args) {
        Account account = new Account();
        account.process(new Command(Command.Action.DEPOSIT, 20));
        System.out.println(account.balance);
        account.process(new Command(Command.Action.WITHDRAW, 200));
        account.process(new Command(Command.Action.WITHDRAW, 5));
        System.out.println(account.balance);

    }
}

class Command
{
    enum Action
    {
        DEPOSIT, WITHDRAW
    }

    public Action action;
    public int amount;
    public boolean success;

    public Command(Action action, int amount)
    {
        this.action = action;
        this.amount = amount;
    }
}

class Account
{
    public int balance;

    public void process(Command c)
    {
        c.success = true;
        switch(c.action) {
            case DEPOSIT:
                this.balance += c.amount;
                break;
            case WITHDRAW:
                if(this.balance - c.amount > 0) {
                    this.balance -= c.amount;
                    break;
                }
                c.success = false;
        }
    }
}
