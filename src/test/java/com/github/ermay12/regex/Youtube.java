package com.github.ermay12.regex;

import static com.github.ermay12.regex.RegexBuilder.*;

public class Youtube {
    public static void main(String[] args) {
        Regex regex =
                startLine()
                .anyAmount(wildcard())
                .or(
                        string("youtu.be/"),
                        string("v/"),
                        string("/u/w/"),
                        string("embed/"),
                        string("watch?")
                ).optional('?')
                .optional('v')
                .optional('=')
                .capture(
                        anyAmount(
                                not(choice('#', '&', '?'))
                        )
                ).anyAmount(wildcard()).build();
    }
}
