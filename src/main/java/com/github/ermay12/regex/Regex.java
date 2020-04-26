package com.github.ermay12.regex;

public class Regex {
    private String regexString;

    private Regex(String regexString) {
        this.regexString = regexString;
    }

    public static Regex fromRawRegex(String regex) {
        return new Regex(regex);
    }

    public String toString() {
        return regexString;
    }
}
