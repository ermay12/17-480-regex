package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class PasswordClientCode {
    @Test
    public void validatePassword() {
        Regex regex = concatenate(
                LINE_START,
                lookahead(concatenate(anyAmount(WILDCARD), range('a', 'z'))),
                lookahead(concatenate(anyAmount(WILDCARD), range('A', 'Z'))),
                lookahead(concatenate(anyAmount(WILDCARD), DIGIT)),
                lookahead(concatenate(anyAmount(WILDCARD), not(WORD_CHARACTER))),
                repeatAtLeast(WILDCARD, 8),
                LINE_END
        );

        String input = "pAssword1234!";
        assert(regex.doesMatch(input));
    }
}
