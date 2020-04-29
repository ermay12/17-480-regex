package com.github.ermay12.regex;

public class CharacterClass extends Regex {
    private CharacterClass(String... components) {
        super(components);
    }

    @Override
    protected String selfAsGrouped() {
        return getRawRegex();
    }

    public static final CharacterClass WILDCARD = new CharacterClass(".");
    public static final CharacterClass DIGIT = new CharacterClass("\\d");
    public static final CharacterClass WORD_CHARACTER = new CharacterClass("\\w");
    public static final CharacterClass WHITESPACE = new CharacterClass("\\s");

    public static CharacterClass character(char c) {
        return new CharacterClass(sanitized(c));
    }

    public static CharacterClass range(char c1, char c2) {
        return new CharacterClass("[", sanitized(c1), "-", sanitized(c2), "]");
    }

    //TODO(astanesc): More rigorous implementation!
    public static CharacterClass without(CharacterClass c) {
        if(c.getRawRegex().charAt(0) == '[') {
            return new CharacterClass("[^", c.getRawRegex().substring(2));
        } else {
            return new CharacterClass("[^", c.getRawRegex(), "]");
        }
    }

    public static CharacterClass choice(char... choices) {
        StringBuilder regex = new StringBuilder();
        regex.append("[");
        for(char choice : choices) {
            regex.append(sanitized(choice));
        }
        regex.append("]");
        return new CharacterClass(regex.toString());
    }

    public static CharacterClass union(CharacterClass... choices) {
        if(choices.length > 1) {
            StringBuilder regex = new StringBuilder();
            regex.append("[");
            for (CharacterClass c : choices) {
                regex.append(c.getRawRegex());
            }
            regex.append("");
            return new CharacterClass(regex.toString());
        } else if(choices.length == 1) {
            return choices[0];
        } else {
            return new CharacterClass("[]");
        }
    }
}
