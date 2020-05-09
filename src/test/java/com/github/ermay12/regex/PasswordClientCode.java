package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class PasswordClientCode {
    public static void main(String[] args) {
        Regex regex = new Regex(
                LINE_START,
                lookahead(new Regex(anyAmount(WILDCARD), single(range('a', 'z')))),
                lookahead(new Regex(anyAmount(WILDCARD), single(range('A', 'Z')))),
                lookahead(new Regex(anyAmount(WILDCARD), single(DIGIT))),
                lookahead(new Regex(anyAmount(WILDCARD), single(not(WORD_CHARACTER)))),
                repeatAtLeast(WILDCARD, 8),
                LINE_END
        );
        System.out.println(regex);
    }
}
