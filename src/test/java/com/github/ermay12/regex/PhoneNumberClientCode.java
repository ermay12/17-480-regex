package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static com.github.ermay12.regex.CapturingGroup.*;

public class PhoneNumberClientCode {
    public static void main(String[] args) {
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
        System.out.println(phoneNumber);
    }
}
