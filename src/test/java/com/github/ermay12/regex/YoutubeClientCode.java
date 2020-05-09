package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class YoutubeClientCode {
    @Test
    public void checkYoutubeLink() {
        Regex regex = concatenate(
                LINE_START,
                anyAmount(WILDCARD),
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
                anyAmount(
                        not(union('#', '&', '?'))
                ),
                anyAmount(WILDCARD)
        );

        String input = "Check this out! https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        assert(regex.doesMatch(input));
    }
}
