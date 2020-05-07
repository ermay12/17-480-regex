package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;

public final class RomanNumeralTest {
  @Test
  public void testRomanNumeral() {
    RegularExpression regex = concatenate(
        LINE_START,
        lookahead(CharacterClass.WILDCARD),
        anyAmount("M"),
        capture(
            oneOf(
                concatenate(string("C"), CharacterClass.union('M', 'D')),
                concatenate(optional("D"), repeat("C", 0, 3))
            )
        ),
        capture(
            oneOf(
                concatenate(string("X"), CharacterClass.union('C', 'L')),
                concatenate(optional("L"), repeat("X", 0, 3))
            )
        ),
        capture(
            oneOf(
                concatenate(string("I"), CharacterClass.union('X', 'V')),
                concatenate(optional("V"), repeat("I", 0, 3))
            )
        )
    );
  }
}
