package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class PasswordTest {
  @Test
  public void testPassword() {
    RegularExpression regex = concatenate(
        LINE_START,
        lookahead(concatenate(anyAmount(WILDCARD), single(range('a', 'z')))),
        lookahead(concatenate(anyAmount(WILDCARD), single(range('A', 'Z')))),
        lookahead(concatenate(anyAmount(WILDCARD), single(DIGIT))),
        lookahead(concatenate(anyAmount(WILDCARD), single(not(WORD_CHARACTER)))),
        repeatAtLeast(WILDCARD, 8),
        LINE_END
    );
  }
}
