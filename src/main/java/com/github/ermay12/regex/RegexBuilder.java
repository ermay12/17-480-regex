package com.github.ermay12.regex;

public class RegexBuilder {
    protected StringBuilder regex;

    private RegexBuilder() {
        regex = new StringBuilder();
    }

    protected void sanitizedAppend(char c) {
        switch(c) {
            case '?':
            case '\\':
            case '-':
            case '=':
            case '[':
            case ']':
            case '(':
            case ')':
            case '{':
            case '}':
            case '<':
            case '>':
            case '!':
            case '*':
            case '.':
            case '+':
            case '^':
            case '$':
            case '|':
                regex.append('\\');
            default:
                regex.append(c);
        }
    }

    private static Regex badEscapeChecker = Regex.fromRawRegex(".*\\\\E.*");

    protected void sanitizedAppend(String s) {
        //TODO(astanesc): Use a regex or .contains?
        if(s.contains("\\E") || s.length() == 1) {
            for (char c : s.toCharArray()) {
                sanitizedAppend(c);
            }
        } else {
            regex.append("\\Q");
            regex.append(s);
            regex.append("\\E");
        }
    }

    protected void addSelfAsGrouped(StringBuilder r) {
        r.append("(?:");
        r.append(regex);
        r.append(")");
    }

    public RegexBuilder lookahead(RegexBuilder regex) {
        this.regex.append("(?=");
        this.regex.append(regex.regex);
        this.regex.append(")");
        return this;
    }

    public RegexBuilder anyAmount(char c) {
        sanitizedAppend(c);
        this.regex.append("*");
        return this;
    }

    public RegexBuilder anyAmount(RegexBuilder s) {
        s.addSelfAsGrouped(regex);
        this.regex.append("*");
        return this;
    }

    public RegexBuilder atLeastOne(RegexBuilder s) {
        s.addSelfAsGrouped(regex);
        this.regex.append("+");
        return this;
    }

    public RegexBuilder capture(RegexBuilder s) {
        this.regex.append("(");
        this.regex.append(s.regex);
        this.regex.append(")");
        return this;
    }

    private Regex regexLabel = Regex.fromRawRegex("[a-zA-Z][a-zA-Z0-9]*");

    public RegexBuilder capture(RegexBuilder s, String label) {
        //TODO(astanesc): Ensure that label matches the regexLabel

        this.regex.append("(?<");
        this.regex.append(label);
        this.regex.append(">");
        this.regex.append(s.regex);
        this.regex.append(")");

        return this;
    }

    public RegexBuilder choice(char c1, char... choices) {
        this.regex.append("[");
        sanitizedAppend(c1);
        for(char choice : choices) {
            sanitizedAppend(choice);
        }
        this.regex.append("]");

        return this;
    }

    public RegexBuilder optional(char c) {
        sanitizedAppend(c);
        this.regex.append("?");
        return this;
    }

    public RegexBuilder optional(RegexBuilder s) {
        s.addSelfAsGrouped(regex);
        this.regex.append("?");
        return  this;
    }

    public RegexBuilder repeat(char c, int min, int max) {
        sanitizedAppend(c);
        regex.append("{");
        regex.append(min);
        regex.append(",");
        regex.append(max);
        regex.append("}");
        return this;
    }

    public RegexBuilder repeat(RegexBuilder g, int min, int max) {
        g.addSelfAsGrouped(regex);
        regex.append("{");
        regex.append(min);
        regex.append(",");
        regex.append(max);
        regex.append("}");
        return this;
    }

    public RegexBuilder repeatAtLeast(RegexBuilder g, int min) {
        g.addSelfAsGrouped(regex);
        regex.append("{");
        regex.append(min);
        regex.append(",");
        regex.append("}");
        return this;
    }

    public RegexBuilder or(RegexBuilder s1, RegexBuilder... ss) {
        regex.append("(?:");
        regex.append(s1.regex);
        for (RegexBuilder s : ss) {
            regex.append("|");
            regex.append(s.regex);
        }
        regex.append(")");

        return this;
    }

    public RegexBuilder single(CharacterClass c) {
        regex.append(c.regex);
        return this;
    }

    public RegexBuilder string(String s) {
        sanitizedAppend(s);
        return this;
    }

    public RegexBuilder then(Regex r) {
        this.regex.append(r.toString());
        return this;
    }

    public RegexBuilder startLine() {
        this.regex.append("^");
        return this;
    }

    public RegexBuilder endLine() {
        this.regex.append("$");
        return this;
    }

    public Regex build() {
            return Regex.fromRawRegex(this.regex.toString());
        }

    private static class CapturingGroup extends RegexBuilder {
        @Override
        protected void addSelfAsGrouped(StringBuilder r) {
            r.append(regex);
        }
    }

    public static class CharacterClass extends RegexBuilder {
        private CharacterClass() {}
        private CharacterClass(String start) { regex.append(start); }

        @Override
        protected void addSelfAsGrouped(StringBuilder r) {
            r.append(regex);
        }

        public static CharacterClass wildcard() {
            return new CharacterClass(".");
        }

        public static CharacterClass character(char c) {
            CharacterClass cc = new CharacterClass();
            cc.sanitizedAppend(c);
            return cc;
        }

        public static CharacterClass range(char c1, char c2) {
            CharacterClass cc = new CharacterClass();
            cc.regex.append("[");
            cc.sanitizedAppend(c1);
            cc.regex.append("-");
            cc.sanitizedAppend(c2);
            cc.regex.append("]");
            return cc;
        }

        //TODO(astanesc): More rigorous implementation!
        public static CharacterClass not(CharacterClass c) {
            if(c.regex.charAt(0) == '[') {
                c.regex.insert(1, '^');
            } else {
                c.regex.insert(0, "[^");
                c.regex.append("]");
            }
            return c;
        }

        public static CharacterClass digit() {
            return new CharacterClass("\\d");
        }

        public static CharacterClass wordCharacter() {
            return new CharacterClass("\\w");
        }

        public static CharacterClass whitespace() {
            return new CharacterClass("\\s");
        }

        public static CharacterClass choice(char... choices) {
            CharacterClass cc = new CharacterClass();
            cc.regex.append("[");
            for(char choice : choices) {
                cc.sanitizedAppend(choice);
            }
            cc.regex.append("]");
            return cc;
        }

        public static CharacterClass union(CharacterClass... choices) {
            if(choices.length > 1) {
                CharacterClass cc = new CharacterClass();
                cc.regex.append("[");
                for (CharacterClass c : choices) {
                    cc.regex.append(c.regex);
                }
                cc.regex.append("");
                return cc;
            } else if(choices.length == 0) {
                return choices[0];
            } else {
                return new CharacterClass("[]");
            }
        }
    }

    public static class StaticHelpers {
        public static RegexBuilder startLine() {
            return new RegexBuilder().startLine();
        }

        public static RegexBuilder regex(Regex r) {
            return new RegexBuilder().then(r);
        }

        public static CharacterClass wildcard() {
            return CharacterClass.wildcard();
        }

        public static RegexBuilder capture(RegexBuilder s) {
            return new RegexBuilder().capture(s);
        }

        public static RegexBuilder or(RegexBuilder s1, RegexBuilder... ss) {
            return new RegexBuilder().or(s1, ss);
        }

        public static RegexBuilder string(String s) {
            return new RegexBuilder().string(s);
        }

        public static CharacterClass character(char c) {
            return CharacterClass.character(c);
        }

        public static CharacterClass range(char c1, char c2) {
            return CharacterClass.range(c1, c2);
        }

        public static RegexBuilder optional(char c) {
            return new RegexBuilder().optional(c);
        }

        public static RegexBuilder optional(RegexBuilder s) {
            return new RegexBuilder().optional(s);
        }

        public static RegexBuilder anyAmount(RegexBuilder s) {
            return new RegexBuilder().anyAmount(s);
        }

        public static RegexBuilder atLeastOne(RegexBuilder s) {
            return new RegexBuilder().atLeastOne(s);
        }

        public static CharacterClass not(CharacterClass c) {
            return CharacterClass.not(c);
        }

        public static CharacterClass digit() {
            return CharacterClass.digit();
        }

        public static CharacterClass wordCharacter() {
            return CharacterClass.wordCharacter();
        }

        public static CharacterClass whitespace() {
            return CharacterClass.whitespace();
        }

        public static CharacterClass choice(char... choices) {
            return CharacterClass.choice(choices);
        }

        public static CharacterClass union(CharacterClass... c) { return CharacterClass.union(c); }
    }
}
