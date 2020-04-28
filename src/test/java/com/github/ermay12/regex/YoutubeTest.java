package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;
import static org.junit.Assert.assertEquals;

public class YoutubeTest {
    @Test
    public void youtubeTest() {

      //without static imports
      RegexGroup group = new Regex(
          Charset.without('#', '&', '?').anyAmount()
      );

      Regex regex =
          new Regex(
              Regex.LINESTART,
              Charset.WILDCARD.anyAmount(),
              Regex.oneOf(
                  Regex.string("youtu.be/"),
                  Regex.string("v/"),
                  Regex.string("/u/w/"),
                  Regex.string("embed/"),
                  Regex.string("watch?")
              ),
              Regex.optional("?"),
              Regex.optional("v"),
              Regex.optional("="),
              group,
              Regex.string("asdasd").anyAmount()
          );



      //with static imports
      RegexGroup group = new Regex(
          Charset.without('#', '&', '?').anyAmount()
      );

      Regex regex =
          new Regex(
              LINESTART,
              WILDCARD.anyAmount(),
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
              string("asdasd").anyAmount()
          );

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
