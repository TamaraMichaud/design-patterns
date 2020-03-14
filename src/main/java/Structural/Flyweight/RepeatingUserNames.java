package Structural.Flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class RepeatingUserNames {
    public static void main(String[] args) {
        User john = new User("John Smith");
        User jane = new User("Jane Smith"); // << these two users have overlapping names
        // ^^ we could store the names separately and save 5 bytes. image times 1 million...
        User2 jill = new User2("Jill Jones");
        User2 bob = new User2("Bob Jones");
        // ^^ now we save some memory!
        System.out.println(bob.getName());

    }
}

class User2 {
    static List<String> strings = new ArrayList<>();
    private int[] names; // << these will be the pointers to the strings list

    public String getName(){
        StringBuilder name = new StringBuilder();
        for (int value : names) {
            name.append("> ").append(strings.get(value));
        }
        return name.toString();
    }

    public User2(String fullname) { // << we want this to stay the same as our original user

        // utility function to either get a value from the strings collection, or add a value. and return the integer
        // of the index to that value
        Function<String, Integer> getOrAdd = (String s) -> {
            // lookup the string
            int index = strings.indexOf(s);
            if(index != -1) return index;
            else {
                strings.add(s);
                return strings.size() -1;
            }
        };

        names = Arrays.stream(fullname.split(" "))
                    .mapToInt(getOrAdd::apply)
                    .toArray();

    }
}


class User {
    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }
}