package Behavioural.Interpreter;

// handmade rather than using any framework

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class bHandmadeInterpreterLexing {
    public static void main(String[] args) {
        String input = "(13+4)-(12+1)";
        // imagine a user has typed this on the command-line. we want to evaluate it
        List<Token> tokens = lex(input);
//        System.out.println(tokens);
        System.out.println(tokens.stream().map(t -> t.toString()).collect(Collectors.joining("\t")));
    }

    static List<Token> lex(String inputText){

        ArrayList<Token> list = new ArrayList<>();
        for(int i = 0; i < inputText.length(); i++){
            switch (inputText.charAt(i)){

                case '+': list.add(new Token(Token.Type.PLUS, "+"));
                    break;
                case '-': list.add(new Token(Token.Type.MINUS, "-"));
                    break;
                case '(': list.add(new Token(Token.Type.L_PAREN, "("));
                    break;
                case ')': list.add(new Token(Token.Type.R_PAREN, ")"));
                    break;
                default:
                    // could be multiple numbers in a row so we can't just grab the first char
                    StringBuilder sb = new StringBuilder("" + inputText.charAt(i));
                    // ^^ already grab the first char
                    for(int x = i+1; x < inputText.length(); x++){
                        if(Character.isDigit(inputText.charAt(x))) {
                            sb.append(inputText.charAt(x));
                            i++;
                        } else {
                            break;
                        }
                    }

                    list.add(new Token(Token.Type.INTEGER, sb.toString()));
                    break;


            }
        }
        return list;
    }
}

// token for the opening round brackets, a token for the numbers, and a token for the operator(s)
class Token {
    public enum Type {
        INTEGER,
        PLUS,
        MINUS,
        L_PAREN,
        R_PAREN
    }

    public Type type;
    public String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "`" + this.text + "`";
    }
}