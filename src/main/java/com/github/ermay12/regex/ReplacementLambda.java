package com.github.ermay12.regex;

@FunctionalInterface
public interface ReplacementLambda {
    String matchCallback(RegexMatch match);
}
