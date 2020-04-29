package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharClass.*;
import static org.junit.Assert.assertEquals;

public class YoutubeTest {
  @Test
  public void youtubeTest() {
    RegexGroup group = new RegexGroup(
        anyAmount(
            CharClass.without('#', '&', '?')
        )
    );

    Regex regex = new Regex(
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
        group,
        anyAmount(WILDCARD)
    );
    assertEquals("^.*(?:\\Qyoutu.be/\\E|\\Qv/\\E|\\Q/u/w/\\E|\\Qembed/\\E|\\Qwatch?\\E)\\??v?\\=?([^#&\\?]*).*",
        regex.toString());
  }
}
