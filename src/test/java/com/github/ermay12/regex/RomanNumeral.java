package com.github.ermay12.regex;

import static com.github.ermay12.regex.RegexBuilder.*;

public final class RomanNumeral {
    static final String s = "^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})";

    public static void main(String[] args) {
        Regex regex = startLine()
                .lookahead(wildcard())
                .anyAmount('M')
                .capture(
                        or(
                                string("C").choice('M', 'D'),
                                optional('D').repeat('C', 0, 3)
                        )
                ).capture(
                        or(
                                string("X").choice('C', 'L'),
                                optional('L').repeat('X', 0, 3)
                        )
                ).capture(
                        or(
                                string("I").choice('X', 'V'),
                                optional('V').repeat('I', 0, 3)
                        )
                ).build();
    }
}
