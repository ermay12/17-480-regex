package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;
import static org.junit.Assert.assertEquals;

public class PasswordTest {
    @Test
    public void testPassword() {
        Regex regex =
                startLine()
                .lookahead(anyAmount(wildcard()).single(range('a', 'z')))
                .lookahead(anyAmount(wildcard()).single(range('A', 'Z')))
                .lookahead(anyAmount(wildcard()).single(digit()))
                .lookahead(anyAmount(wildcard()).single(not(wordCharacter())))
                .repeatAtLeast(wildcard(), 8)
                .endLine()
                .build();
        assertEquals("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w]).{8,}$",
                     regex.toString());
    }
}
