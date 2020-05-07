package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static org.junit.Assert.assertEquals;

public class PhoneNumberTest {
    @Test
    public void testPhoneNumber() {
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
            phoneNumber = new Regex(phoneNumber,
                                    capture(DIGIT),
                                    separator);
        }

        assertEquals("(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?(\\d)(?:(?:\\-|\\(|\\)|\\s))?",
                     phoneNumber.toString());
    }
}
