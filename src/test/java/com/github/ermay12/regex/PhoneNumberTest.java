package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static org.junit.Assert.assertEquals;

public class PhoneNumberTest {
  @Test
  public void testPhoneNumber() {
    RegularExpression separator =
        optional(
            oneOf(
                string("-"),
                string("("),
                string(")"),
                WHITESPACE
            )
        );

    RegularExpression phoneNumber = separator;
    for (int i = 0; i < 10; i++) {
      phoneNumber = concatenate(phoneNumber,
          capture(DIGIT),
          separator);
    }
  }
}
