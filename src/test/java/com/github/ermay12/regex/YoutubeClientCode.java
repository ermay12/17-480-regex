package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static com.github.ermay12.regex.CapturingGroup.*;

public class YoutubeClientCode {
    public static void main(String[] args) {
        Regex regex = new Regex(
                LINE_START,
                anyAmount(CharacterClass.WILDCARD),
                oneOf(
                        string("youtu.be/"),
                        string("v/"),
                        string("/u/w/"),
                        string("embed/"),
                        string("watch?")
                ),
                optional("?"),
                optional("v"),
                optional("="),
                capture(anyAmount(
                        not(CharacterClass.union('#', '&', '?'))
                )),
                anyAmount(CharacterClass.WILDCARD)
        );
        System.out.println(regex);
    }
}
