package Behavioural.Command;

// YOU SHALL NOT PASS!
// when you make a statement in java, it is perishable -> you can't just "undo" e.g. a field-assignment
// you cannot serialize actions into memory so that you can roll them back...
// what you'd need is an object to represent an operation. e.g. X should do w(). or X should change field Y to = Z.
//
// command pattern is an object which represents an instruction to perform a particular action
// contains all the information required so that the action can be taken

// e.g. bank account modification

import java.util.List;

public class aCommand {
    public static void main(String[] args) {
        BankAccount ba = new BankAccount();
        System.out.println(ba); // balance should be zero

        List<BankAccountCommand> commands = List.of(
                new BankAccountCommand(ba, BankAccountCommand.Action.DEPOSIT, 100),
                new BankAccountCommand(ba, BankAccountCommand.Action.WITHDRAW, 900) // << should fail.
        );
        for(BankAccountCommand c: commands) {
            c.call();
            System.out.println(ba);
        }

        System.out.print(" -- Reversing Bank Account Changes... maybe -- ");
        // remember, to undo you must reverse the order as well as the action
        for(int i = commands.size() -1 ; i >= 0; i--){
            commands.get(i).undo();
            System.out.println(ba);
        }

    }
}

interface Command {
    void call();
    void undo();
}

class BankAccountCommand implements Command {

    private BankAccount account;
    private boolean succeeded; // << new: so undo() will work.  HAD TO CHANGE THE BANKACCOUNT CLASS THOUGH!

    public enum Action {
        DEPOSIT, WITHDRAW
    }
    private Action action;
    private int amount;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }

    @Override
    public void call() {
        switch(action){

            case DEPOSIT:
                account.deposit(amount);
                break;
            case WITHDRAW:
                succeeded = account.withdraw(amount);
                break;
        }
    }

    @Override
    public void undo(){

        // assuming that deposit and withdrawal are symmetric, so to undo a deposit, we make it a withdrawal etc...
        // (this assumption is incorrect... i guess because the rejected withdrawal should not have a successful 'undo' ? yes)
        switch(action){

            case WITHDRAW:
                if(!succeeded) break;
                account.deposit(amount);
                break;
            case DEPOSIT:
                account.withdraw(amount);
                break;
        }
    }
}


// can deposit and withdraw... but we want to audit these deposits; log them somwhere. without changing this class...
class BankAccount {
    private int balance;
    private int overdraftLimit = -500;

    public void deposit(int amount){
        balance += amount;
        System.out.println("Deposited " + amount);
        System.out.println("Balance is now " + balance);
    }

    public boolean withdraw(int amount) {
        if(balance - amount >= overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew " + amount);
            System.out.println("Balance is now " + balance);
            return true;
        } else {
            System.out.println(String.format("Woops, withdrawing %d would take you overdrawn", amount));
            return false;
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}