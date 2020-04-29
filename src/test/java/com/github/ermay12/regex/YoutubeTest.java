package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static org.junit.Assert.assertEquals;

public class YoutubeTest {
    @Test
    public void youtubeTest() {
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
                optional('?'),
                optional('v'),
                optional('='),
                capture(anyAmount(
                        CharacterClass.without(CharacterClass.choice('#', '&', '?'))
                )),
                anyAmount(CharacterClass.WILDCARD)
        );
        assertEquals("^.*(?:\\Qyoutu.be/\\E|\\Qv/\\E|\\Q/u/w/\\E|\\Qembed/\\E|\\Qwatch?\\E)\\??v?\\=?([^#&\\?]*).*",
                     regex.toString());
    }
}
