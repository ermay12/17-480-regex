package com.github.ermay12.regex;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;

public class Password {
    public static void main(String[] args) {
        Regex regex =
                startLine()
                .lookahead(anyAmount(wildcard()).single(range('a', 'z')))
                .lookahead(anyAmount(wildcard()).single(range('A', 'Z')))
                .lookahead(anyAmount(wildcard()).single(digit()))
                .lookahead(anyAmount(wildcard()).single(not(wordCharacter())))
                .repeatAtLeast(wildcard(), 8)
                .endLine()
                .build();
    }
}
