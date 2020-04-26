package com.github.ermay12.regex;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;

public class PhoneNumber {
    public static void main(String[] args) {
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

        System.out.println(phoneNumber);
    }
}
