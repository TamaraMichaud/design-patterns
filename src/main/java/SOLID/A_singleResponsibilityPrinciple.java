package SOLID;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class A_singleResponsibilityPrinciple {

    public static void main(String[] args) throws Exception {

        // Journal class should not be concerned with anything but journal entry management
        Journal j = new Journal();
        j.addEntry("I cried today");
        j.addEntry("I ate a bug");

        // Persistence class can handle saving/loading. It could potentially handle more than journals
        Persistence p = new Persistence();
        String filename = "c:\\path\\to\\file\\todaysJournalEntries.txt";
        p.saveToFile(j, filename, true);

//        Runtime.getRuntime().exec("notepad.exe " + filename);
        // ^^ if we were actually saving to a file, we can open that file with this command!

        System.out.println(j.toString());
    }
}


class Journal {

    private final List<String> entries;
    // ^^ this doesn't stop us adding records, simply stops us changing "entries" from a list
    private int counter;

    public Journal() {
        this.entries = new ArrayList<String>();
        this.counter = 0;
    }

    public void addEntry(String journalEntry) {
        entries.add("" + (++counter) + ": " + journalEntry);
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder();
        for (String entry : entries) {
            output.append(entry + "\n");
        }
        return output.toString();
    }
}

class Persistence {
    public void saveToFile(Journal journal,
                           String filename,
                           boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(filename).exists()) {
//            try (PrintStream out = new PrintStream(filename)) {
//                out.println(journal.toString());
//            }
            System.out.println("(not) Saving to " + filename);
        }
        // ^^ don't actually want to print anything so we'll just sout.
    }

    public void load(String filename) {
//    public Journal load(String filename) {
        System.out.println("(not) Loading journal from file: " + filename);
    }

    public void load(URL url) {
//    public Journal load(URL url) {
        System.out.println("(not) Loading journal from url: " + url);
    }


}