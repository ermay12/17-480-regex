package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static org.junit.Assert.assertEquals;

public final class RomanNumeralTest {

    @Test
    public void testRomanNumeral() {
        Regex regex = new Regex(
                LINE_START,
                lookahead(CharacterClass.WILDCARD),
                anyAmount('M'),
                capture(
                        oneOf(
                                new Regex(string("C"), CharacterClass.choice('M', 'D')),
                                new Regex(optional('D'), repeat('C', 0, 3))
                        )
                ),
                capture(
                        oneOf(
                                new Regex(string("X"), CharacterClass.choice('C', 'L')),
                                new Regex(optional('L'), repeat('X', 0, 3))
                        )
                ),
                capture(
                        oneOf(
                                new Regex(string("I"), CharacterClass.choice('X', 'V')),
                                new Regex(optional('V'), repeat('I', 0, 3))
                        )
                )
        );
        assertEquals("^(?=.)M*((?:C[MD]|D?C{0,3}))((?:X[CL]|L?X{0,3}))((?:I[XV]|V?I{0,3}))",
                     regex.toString());
    }
}
