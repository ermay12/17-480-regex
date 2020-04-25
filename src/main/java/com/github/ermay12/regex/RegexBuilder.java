package com.github.ermay12.regex;

public class RegexBuilder {
    private RegexBuilder() {}

    public static class Standard {
        private Standard() {}

        public Standard lookahead(Standard regex) {
            return this;
        }

        public Standard anyAmount(char c) {
            return this;
        }

        public Standard anyAmount(Standard s) {
            return this;
        }

        public Standard atLeastOne(Standard s) {
            return this;
        }

        public Standard capture(Standard s) {
            return this;
        }

        public Standard choice(char... choices) {
            return this;
        }

        public Standard optional(char c) {
            return  this;
        }

        public Standard optional(Standard s) {
            return  this;
        }

        public Standard repeat(char c, int min, int max) {
            return this;
        }

        public Standard repeat(Standard g, int min, int max) {
            return this;
        }

        public Standard repeatAtLeast(Standard g, int min) {
            return this;
        }

        public Standard or(Standard... ss) {
            return this;
        }

        public Standard single(CharacterClass c) {
            return this;
        }

        public Standard string(String s) {
            return this;
        }

        public Standard then(Regex r) {
            return this;
        }

        public Standard endLine() {
            return this;
        }

        public Regex build() {
            return null;
        }
    }

    public static Standard startLine() {
        return new Standard();
    }

    public static Standard regex(Regex r) {
        return new Standard();
    }

    public static class CharacterClass extends Standard {

    }

    public static CharacterClass wildcard() {
        return new CharacterClass();
    }

    public static Standard capture(Standard s) {
        return new Standard();
    }

    public static Standard or(Standard s1, Standard... ss) {
        return new Standard();
    }

    public static Standard string(String s) {
        return new Standard();
    }

    public static CharacterClass character(char c) {
        return new CharacterClass();
    }

    public static CharacterClass range(char c1, char c2) {
        return new CharacterClass();
    }

    public static Standard optional(char c) {
        return new Standard();
    }

    public static Standard optional(Standard s) {
        return new Standard();
    }

    public static Standard anyAmount(Standard s) {
        return new Standard();
    }

    public static Standard atLeastOne(Standard s) {
        return new Standard();
    }

    public static CharacterClass not(CharacterClass c) {
        return new CharacterClass();
    }

    public static CharacterClass digit() {
        return new CharacterClass();
    }

    public static CharacterClass wordCharacter() {
        return new CharacterClass();
    }

    public static CharacterClass whitespace() {
        return new CharacterClass();
    }

    public static CharacterClass choice(char... choices) {
        return new CharacterClass();
    }
}
