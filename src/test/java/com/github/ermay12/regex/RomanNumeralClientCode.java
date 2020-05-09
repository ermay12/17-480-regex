package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;

public class RomanNumeralClientCode {
    public static void main(String[] args) {
        Regex regex = new Regex(
                LINE_START,
                lookahead(CharacterClass.WILDCARD),
                anyAmount("M"),
                capture(
                        oneOf(
                                new Regex(string("C"), CharacterClass.union('M', 'D')),
                                new Regex(optional("D"), repeat("C", 0, 3))
                        )
                ),
                capture(
                        oneOf(
                                new Regex(string("X"), CharacterClass.union('C', 'L')),
                                new Regex(optional("L"), repeat("X", 0, 3))
                        )
                ),
                capture(
                        oneOf(
                                new Regex(string("I"), CharacterClass.union('X', 'V')),
                                new Regex(optional("V"), repeat("I", 0, 3))
                        )
                )
        );
        System.out.println(regex);
    }
}
