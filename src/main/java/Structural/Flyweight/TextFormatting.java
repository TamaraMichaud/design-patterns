package Structural.Flyweight;

// capitalise parts of a string

import java.util.ArrayList;
import java.util.List;

public class TextFormatting {
    public static void main(String[] args) {
        FormattedText1 ft = new FormattedText1("format me please");
        ft.capitalize(7, 9);
        System.out.println(ft.toString());

        // more efficient
        BetterFormattedText bft = new BetterFormattedText("I am pretend bold");
        bft.getRange(13, 18).setBold(); // << nicer api too. set range then apply action to it
        System.out.println(bft.toString());
    }
}

class BetterFormattedText {

    private String plainText;
    private List<TextRange> formatting = new ArrayList<>(); // << this allows us to add any number of formattings

    public BetterFormattedText(String plainText) {
        this.plainText = plainText;
    }

    // utility method to expose textRange class
    public TextRange getRange(int start, int end){
        TextRange range = new TextRange(start, end);
        formatting.add(range);
        return range;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++){
            // different than the previous toString for FormattedText
            char c = plainText.charAt(i);
            for (TextRange range : formatting) {
                if(range.covers(i) && range.bold) {
                    c = Character.toUpperCase(c);
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // nested class that defines a text range
    public class TextRange {
        public int start, end;
        public boolean capitalize, bold, italic;

        public void setBold(){ this.bold = true;}
        public void setItalic(){ this.italic = true;}
        public void setUpper(){ this.capitalize = true;}

        public TextRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean covers(int position){
            return position >= start && position <= end;
        }

    }



}



// ugly way, memory waste
// we store a boolean for every single character provided
class FormattedText1 {
    private String plainText;
    private boolean[] capitalize;

    public FormattedText1(String plainText) {
        this.plainText = plainText;
        this.capitalize = new boolean[plainText.length()];
    }

    public void capitalize(int start, int end){
        for(int i = start; i <= end; i++){
            capitalize[i] = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            char c = plainText.charAt(i);
            sb.append(capitalize[i] ? Character.toUpperCase(c) : c);
        }
        return sb.toString();
    }
}
