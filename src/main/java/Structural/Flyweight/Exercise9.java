package Structural.Flyweight;

import java.util.*;

public class Exercise9 {
    public static void main(String[] args) {
        var sentence = new Sentence("hello world");
        sentence.getWord(1).capitalize = true; // set upper
        System.out.println(sentence); // writes hello WORLD
    }
}


class Sentence {
    private String plainText;
    private List<WordToken> wordTokens = new ArrayList<>();

    public Sentence(String plainText) {
        this.plainText = plainText;
    }

    public WordToken getWord(int index) {

        WordToken token = new WordToken(index);
        wordTokens.add(token);
        return token;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String[] words = this.plainText.split(" ");
        for(int i = 0 ; i < words.length; i++) {
            String word = words[i];
            for(WordToken wordToken : wordTokens) {
                if(wordToken.isWord(i) && wordToken.capitalize) {
                    sb.append(word.toUpperCase());
                } else {
                    sb.append(word);
                }
                if(i < words.length - 1) sb.append(" ");
            }
        }
        return sb.toString();
    }

    class WordToken {
        public boolean capitalize;
        public int wordIdx;

        public WordToken(int wordIdx) {
            this.wordIdx = wordIdx;
        }

        public boolean isWord(int wordIdx) {
            return this.wordIdx == wordIdx;
        }
    }
}