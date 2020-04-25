package com.github.ermay12.regex;

import static com.github.ermay12.regex.RegexBuilder.*;

public class Email {
    public static void main(String[] args) {
        Regex regex = startLine()
                .atLeastOne(wildcard())
                .string("@")
                .atLeastOne(wildcard())
                .endLine()
                .build();
    }
}
