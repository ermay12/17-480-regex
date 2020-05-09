package com.github.ermay12.regex;


import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;

public class EmailClientCode {
    @Test
    public void checkEmail() {
        Regex regex = new Regex(
                LINE_START,
                capture(atLeastOne(CharacterClass.WILDCARD)),
                string("@"),
                capture(atLeastOne(CharacterClass.WILDCARD)),
                LINE_END
        );
        String input = "hello@example.com";
        if (regex.doesMatch(input)) {
            System.out.println("Matches!");
        } else {
            System.out.println("Invalid email :(");
        }
    }
}
