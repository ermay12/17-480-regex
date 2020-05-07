package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static org.junit.Assert.*;

public class EmailTest {
    @Test
    public void testEmail() {
        Regex regex = new Regex(
                LINE_START,
                capture(atLeastOne(CharacterClass.WILDCARD)),
                string("@"),
                capture(atLeastOne(CharacterClass.WILDCARD)),
                LINE_END
        );
        assertEquals("^(.+)@(.+)$", regex.toString());
    }
}
