package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static org.junit.Assert.assertEquals;

public class PasswordTest {
    @Test
    public void testPassword() {
        Regex regex = new Regex(
                LINE_START,
                lookahead(new Regex(anyAmount(WILDCARD), single(range('a', 'z')))),
                lookahead(new Regex(anyAmount(WILDCARD), single(range('A', 'Z')))),
                lookahead(new Regex(anyAmount(WILDCARD), single(DIGIT))),
                lookahead(new Regex(anyAmount(WILDCARD), single(not(WORD_CHARACTER)))),
                repeatAtLeast(WILDCARD, 8),
                LINE_END
        );
    }
}
