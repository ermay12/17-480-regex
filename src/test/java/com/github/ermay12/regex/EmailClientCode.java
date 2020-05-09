package com.github.ermay12.regex;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Optional;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class EmailClientCode {
    @Test
    public void checkEmail() {
        CapturingGroup nameGroup = capture(anyAmount(not(character('@'))));
        CapturingGroup domainGroup = capture(anyAmount(not(character('.'))), string("."), anyAmount(WILDCARD));

        Regex regex = concatenate(nameGroup, string("@"), domainGroup);

        String input = "hello@example.com";

        Optional<RegexMatch> result = regex.firstMatch(input);
        assert(result.isPresent());
        assertEquals("hello", result.get().group(nameGroup));
        assertEquals("example.com", result.get().group(domainGroup));
    }
}
