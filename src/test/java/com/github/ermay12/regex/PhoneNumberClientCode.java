package com.github.ermay12.regex;

import org.junit.Test;

import java.util.Optional;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class PhoneNumberClientCode {
    @Test
    public void checkPhoneNumber() {
        Regex separator =
                optional(
                        oneOf(
                                string("-"),
                                string("("),
                                string(")"),
                                WHITESPACE
                        )
                );

        Regex phoneNumber = separator;
        for (int i = 0; i < 10; i++) {
            phoneNumber = concatenate(phoneNumber, DIGIT, separator);
        }

        String input = "Call me at (908)555-1234";
        Optional<RegexMatch> result = phoneNumber.firstMatch(input);
        assert(result.isPresent());
    }
}
