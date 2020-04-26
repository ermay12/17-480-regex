package com.github.ermay12.regex;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;

public class Email {
    public static void main(String[] args) {
        Regex regex = startLine()
                .capture(atLeastOne(wildcard()))
                .string("@")
                .capture(atLeastOne(wildcard()))
                .endLine()
                .build();
        System.out.println(regex);
    }
}
