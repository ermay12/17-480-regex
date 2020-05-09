package com.github.ermay12.regex;

import org.junit.Test;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static org.junit.Assert.*;

public class RegexMatchUnitTests {
  @Test
  public void testMatchIndex(){
    CapturingGroup group1 = capture(repeatExactly(WORD_CHARACTER, 2));
    CapturingGroup group2 = capture(repeatExactly(WORD_CHARACTER, 2));
    CapturingGroup group3 = capture(anyAmount(WORD_CHARACTER));
    //todo add word boundary
    Regex regex = concatenate(fromRawRegex("\\b"), group1, group2, group3);

    String bunchOfWords = "this is a list of words of varying length";
    RegexMatch match0 = regex.getMatch(bunchOfWords, 0).get();  //this
    RegexMatch match1 = regex.getMatch(bunchOfWords, 1).get();  //list
    RegexMatch match2 = regex.getMatch(bunchOfWords, 2).get();  //words
    RegexMatch match3 = regex.getMatch(bunchOfWords, 3).get();  //varying
    RegexMatch match4 = regex.getMatch(bunchOfWords, 4).get();  //length

    assertEquals(0, match0.matchIndex());
    assertEquals(2, match2.matchIndex());

    assertEquals(0, match0.matchStartIndex());
    assertEquals("this is a list of words of varying length".length(), match4.matchStopIndex());

    assertEquals(0, match0.groupStartIndex(0));
    assertEquals("this is a ".length(), match1.groupStartIndex(1));
    assertEquals("this is a li".length(), match1.groupStopIndex(1));
    assertEquals("this is a list of words of va".length(), match3.groupStartIndex(group2));
    assertEquals("this is a list of words of vary".length(), match3.groupStopIndex(group2));
  }
}
