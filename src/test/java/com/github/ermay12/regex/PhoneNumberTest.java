package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;
import static org.junit.Assert.assertEquals;

public class PhoneNumberTest {
    @Test
    public void testPhoneNumber() {
        Regex separator =
                optional(
                        or(
                                string("-"),
                                string("("),
                                string(")"),
                                whitespace()
                        )
                ).build();

        RegexBuilder builder = regex(separator);
        for (int i = 0; i < 10; i++) {
            builder.capture(digit()).then(separator);
        }

        Regex phoneNumber = builder.build();


        assertEquals("(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?",
                     phoneNumber.toString());
    }
}
