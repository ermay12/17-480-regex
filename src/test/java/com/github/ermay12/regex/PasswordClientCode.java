package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class PasswordClientCode {
    @Test
    public void validatePassword() {
        Regex regex = new Regex(
                LINE_START,
                lookahead(new Regex(anyAmount(WILDCARD), range('a', 'z'))),
                lookahead(new Regex(anyAmount(WILDCARD), range('A', 'Z'))),
                lookahead(new Regex(anyAmount(WILDCARD), DIGIT)),
                lookahead(new Regex(anyAmount(WILDCARD), not(WORD_CHARACTER))),
                repeatAtLeast(WILDCARD, 8),
                LINE_END
        );

        String input = "pAssword1234!";
        if (regex.doesMatch(input)) {
            System.out.println("Password is valid!");
        } else {
            System.out.println("Password is not valid. Please try again.");
        }
    }
}
