package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class YoutubeClientCode {
    @Test
    public void checkYoutubeLink() {
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
                anyAmount(
                        not(CharacterClass.union('#', '&', '?'))
                ),
                anyAmount(CharacterClass.WILDCARD)
        );

        String input = "Check this out! https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        if (regex.doesMatch(input)) {
            System.out.println("Matches!");
        } else {
            System.out.println("Could not find Youtube link");
        }
    }
}
