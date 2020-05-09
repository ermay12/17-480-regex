package com.github.ermay12.regex;

import org.junit.Test;

import java.util.Optional;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;


public class RomanNumeralClientCode {
    @Test
    public void checkRomanNumeral() {
        Regex regex = concatenate(
                lookahead(union('M', 'D', 'C', 'X', 'L', 'V', 'I')),
                anyAmount("M"),
                oneOf(
                        concatenate(string("C"), union('M', 'D')),
                        concatenate(optional("D"), repeat("C", 0, 3))
                ),
                oneOf(
                        concatenate(string("X"), union('C', 'L')),
                        concatenate(optional("L"), repeat("X", 0, 3))
                ),
                oneOf(
                        concatenate(string("I"), union('X', 'V')),
                        concatenate(optional("V"), repeat("I", 0, 3))
                )
        );
        String input = "Roman numeral MCMXCIX is my favorite.";

        Optional<RegexMatch> result = regex.firstMatch(input);
        assert(result.isPresent());
    }
}
