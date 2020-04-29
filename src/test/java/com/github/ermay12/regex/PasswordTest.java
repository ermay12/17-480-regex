package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static org.junit.Assert.assertEquals;

public class PasswordTest {
    @Test
    public void testPassword() {
        Regex regex = new Regex(
                LINE_START,
                lookahead(new Regex(anyAmount(CharacterClass.WILDCARD), single(CharacterClass.range('a', 'z')))),
                lookahead(new Regex(anyAmount(CharacterClass.WILDCARD), single(CharacterClass.range('A', 'Z')))),
                lookahead(new Regex(anyAmount(CharacterClass.WILDCARD), single(CharacterClass.DIGIT))),
                lookahead(new Regex(anyAmount(CharacterClass.WILDCARD), single(CharacterClass.without(CharacterClass.WORD_CHARACTER)))),
                repeatAtLeast(CharacterClass.WILDCARD, 8),
                LINE_END
        );
        assertEquals("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w]).{8,}$",
                     regex.toString());

    }
}
