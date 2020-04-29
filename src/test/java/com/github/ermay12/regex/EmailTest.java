package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharClass.*;
import static org.junit.Assert.*;

public class EmailTest {
    @Test
    public void testEmail() {
        Regex regex = new Regex(
                LINE_START,
                capture(atLeastOne(WILDCARD)),
                string("@"),
                capture(atLeastOne(WILDCARD)),
                LINE_END
        );
        assertEquals("^(.+)@(.+)$", regex.toString());
    }
}
