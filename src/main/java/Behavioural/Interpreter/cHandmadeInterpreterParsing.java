package Behavioural.Interpreter;

import java.util.List;
import java.util.stream.Collectors;

import static Behavioural.Interpreter.Token.Type.INTEGER;


public class cHandmadeInterpreterParsing {
    public static void main(String[] args) {
        String input = "(13+4)-(12+1)";
        // imagine a user has typed this on the command-line. we want to evaluate it
        List<Token> tokens = bHandmadeInterpreterLexing.lex(input);
//        System.out.println(tokens);
//        System.out.println(tokens.stream().map(t -> t.toString()).collect(Collectors.joining("\t")));       // lambda
        System.out.println(tokens.stream().map(Token::toString).collect(Collectors.joining("\t")));  // method reference
    }
}

interface Element { // all elements are numeric, so they can be evaluated... (related to Tokens from previous class)
    int eval();
}

class Integer implements Element {
    private int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return this.value;
    }
}

class BinaryOperation implements Element {

    public enum Type {
        ADDITION,
        SUBTRACTION
    }

    public Type type;
    public Element left, right;

    static Element parse(List<Token> tokens) {
        BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false; // << to parse binary opration we need the left and right... so we flag

//        for (Token nextToken : tokens) { // << no, in the same way we can have multiple numbers in a row,
        // our tokens will need grouping by the brackets (everything between them)
        for (int i = 0; i < tokens.size(); i++) {
            Token nextToken = tokens.get(i);
            switch (nextToken.type) {

                case INTEGER:
                    Integer intVal_leftOrRight = new Integer(java.lang.Integer.parseInt(nextToken.text));
                    if (!haveLHS) {
                        result.left = intVal_leftOrRight;
                        haveLHS = true;
                    } else {
                        result.right = intVal_leftOrRight;
                    }
                    break;
                case PLUS: result.type = Type.ADDITION;
                    break;
                case MINUS: result.type = Type.SUBTRACTION;
                    break;
                case L_PAREN: // essentially this is "start of block" -> so we iterate till we hit a R_PAREN (end of block)
                    // so we can evaluate that expression, before starting the loop again to evaluate the results ->
                    // i.e. (4+3)-(2+1) becomes 7-3 becomes 4
                    int j = i+1;
                    for(; j<tokens.size(); j++) {
                        if(tokens.get(j).type == Token.Type.R_PAREN) {
                            break;
                        }
                        // TOTALLY LOST!! no way i could write something so convoluted
                        List<Token> subExpression = tokens.stream().skip(i + 1).collect(Collectors.toList());
                        // ^^ stream through the list from ( and break on arrival at ) -> now we have a smaller list from the first list
                        Element element = parse(subExpression); // the content between 2 brackets -> ok i get this part
                        i++;
                        if(!haveLHS) {
                            result.left = element;
                            haveLHS = true;
                        } else {
                            result.right = element;
                        }

                    }


                    break;
                case R_PAREN: // moot
                    break;
            }
        }
        return result;
    }

    @Override
    public int eval() {
        switch (type) {

            case ADDITION:
                return left.eval() + right.eval();
            case SUBTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;
        }
    }
}