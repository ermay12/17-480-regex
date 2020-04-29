package com.github.ermay12.regex;

public class CharClass extends Regex {
    private CharClass(String... components) {
        super(components);
    }

    @Override
    protected String selfAsGrouped() {
        return getRawRegex();
    }

    public static final CharClass WILDCARD = new CharClass(".");
    public static final CharClass DIGIT = new CharClass("\\d");
    public static final CharClass WORD_CHARACTER = new CharClass("\\w");
    public static final CharClass WHITESPACE = new CharClass("\\s");

    public static CharClass character(char c) {
        return new CharClass(sanitized(c));
    }

    public static CharClass range(char c1, char c2) {
        return new CharClass("[", sanitized(c1), "-", sanitized(c2), "]");
    }

    public static CharClass not(CharClass charClass){
        if(charClass.equals(WILDCARD)){
            return new CharClass("[\\n]");
        }
        if(charClass.equals(DIGIT)){
            return new CharClass("\\D");
        }
        if(charClass.equals(WORD_CHARACTER)){
            return new CharClass("\\W");
        }
        if(charClass.equals(WHITESPACE)){
            return new CharClass("\\S");
        }
        String expression = charClass.toString();
        if(expression.charAt(0) == '[' && expression.charAt(1) != '^'){
            return new CharClass("[^", expression.substring(1,expression.length()));
        }
        if(expression.length() > 2 && expression.charAt(1) == '^'){
            return new CharClass("[", expression.substring(2, expression.length()));
        }
        throw new Error("Unsupported Character Class");
    }

    //TODO(astanesc): More rigorous implementation!
    public static CharClass without(char... choices) {
        StringBuilder regex = new StringBuilder();
        regex.append("[^");
        for(char choice : choices) {
            regex.append(sanitized(choice));
        }
        regex.append("]");
        return new CharClass(regex.toString());
    }


    public static CharClass with(char... choices) {
        StringBuilder regex = new StringBuilder();
        regex.append("[");
        for(char choice : choices) {
            regex.append(sanitized(choice));
        }
        regex.append("]");
        return new CharClass(regex.toString());
    }

    public static CharClass union(CharClass... choices) {
        if(choices.length > 1) {
            StringBuilder regex = new StringBuilder();
            regex.append("[");
            for (CharClass c : choices) {
                regex.append(c.getRawRegex());
            }
            regex.append("");
            return new CharClass(regex.toString());
        } else if(choices.length == 1) {
            return choices[0];
        } else {
            return new CharClass("[]");
        }
    }
}
