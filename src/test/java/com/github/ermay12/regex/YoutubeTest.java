package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;
import static org.junit.Assert.assertEquals;

public class YoutubeTest {
    @Test
    public void youtubeTest() {
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
                ).anyAmount(string("asdasd")).build();
        assertEquals("^.*(?:\\Qyoutu.be/\\E|\\Qv/\\E|\\Q/u/w/\\E|\\Qembed/\\E|\\Qwatch?\\E)\\??v?\\=?([^#&\\?]*)(?:\\Qasdasd\\E)*",
                     regex.toString());
    }
}
