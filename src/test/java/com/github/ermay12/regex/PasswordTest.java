package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharClass.*;
import static org.junit.Assert.assertEquals;

public class PasswordTest {
    @Test
    public void testPassword() {
        Regex regex = new Regex(
                LINE_START,
                lookahead(new Regex(anyAmount(WILDCARD), range('a', 'z'))),
                lookahead(new Regex(anyAmount(WILDCARD), range('A', 'Z'))),
                lookahead(new Regex(anyAmount(WILDCARD), DIGIT)),
                lookahead(new Regex(anyAmount(WILDCARD), not(WORD_CHARACTER))),
                repeatAtLeast(WILDCARD, 8),
                LINE_END
        );
        assertEquals("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$",
                     regex.toString());

    }
}
