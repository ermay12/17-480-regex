package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;
import static org.junit.Assert.*;

public class EmailTest {
    @Test
    public void testEmail() {
        Regex regex = startLine()
                .capture(atLeastOne(wildcard()))
                .string("@")
                .capture(atLeastOne(wildcard()))
                .endLine()
                .build();
        assertEquals("^(.+)@(.+)$", regex.toString());
    }
}
