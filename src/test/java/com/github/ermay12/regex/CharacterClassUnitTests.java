package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CharacterClassUnitTests {
  @Test
  public void testBuiltin() {
    assertTrue(WILDCARD.doesMatch("a"));
    assertTrue(WILDCARD.doesMatch("\\"));
    assertTrue(WILDCARD.doesMatch("d"));
    assertTrue(WILDCARD.doesMatch("cab"));

    assertTrue(DIGIT.doesMatch("0"));
    assertTrue(DIGIT.doesMatch("1"));
    assertTrue(DIGIT.doesMatch("5"));
    assertTrue(DIGIT.doesMatch("9"));
    assertFalse(DIGIT.doesMatch("a"));
    assertFalse(DIGIT.doesMatch("z"));
    assertFalse(DIGIT.doesMatch("\\"));

    assertTrue(WORD_CHARACTER.doesMatch("a"));
    assertTrue(WORD_CHARACTER.doesMatch("b"));
    assertTrue(WORD_CHARACTER.doesMatch("z"));
    assertTrue(WORD_CHARACTER.doesMatch("A"));
    assertTrue(WORD_CHARACTER.doesMatch("B"));
    assertTrue(WORD_CHARACTER.doesMatch("Z"));
    assertTrue(WORD_CHARACTER.doesMatch("0"));
    assertTrue(WORD_CHARACTER.doesMatch("1"));
    assertTrue(WORD_CHARACTER.doesMatch("5"));
    assertTrue(WORD_CHARACTER.doesMatch("9"));
    assertTrue(WORD_CHARACTER.doesMatch("_"));
    assertFalse(WORD_CHARACTER.doesMatch("-"));
    assertFalse(WORD_CHARACTER.doesMatch("."));
    assertFalse(WORD_CHARACTER.doesMatch("?"));

    assertTrue(WHITESPACE.doesMatch(" "));
    assertTrue(WHITESPACE.doesMatch("\t"));
    assertTrue(WHITESPACE.doesMatch("\n"));
    assertFalse(WHITESPACE.doesMatch("a"));
    assertFalse(WHITESPACE.doesMatch("0"));
  }

  @Test
  public void testCharacter() {
    CharacterClass c = character('c');

    assertTrue(c.doesMatch("c"));
    assertFalse(c.doesMatch("d"));
    assertFalse(c.doesMatch("b"));
  }

  @Test
  public void testRange() {
    CharacterClass c = range('a', 'd');

    assertTrue(c.doesMatch("a"));
    assertTrue(c.doesMatch("b"));
    assertTrue(c.doesMatch("c"));
    assertTrue(c.doesMatch("d"));
    assertFalse(c.doesMatch("e"));
    assertFalse(c.doesMatch("A"));
    assertFalse(c.doesMatch("."));
  }

  @Test
  public void testNot() {

    assertFalse(not(WILDCARD).doesMatch("a"));
    assertFalse(not(WILDCARD).doesMatch("\\"));
    assertFalse(not(WILDCARD).doesMatch("d"));
    assertFalse(not(WILDCARD).doesMatch("cab"));

    assertFalse(not(DIGIT).doesMatch("0"));
    assertFalse(not(DIGIT).doesMatch("1"));
    assertFalse(not(DIGIT).doesMatch("5"));
    assertFalse(not(DIGIT).doesMatch("9"));
    assertTrue(not(DIGIT).doesMatch("a"));
    assertTrue(not(DIGIT).doesMatch("z"));
    assertTrue(not(DIGIT).doesMatch("\\"));

    assertFalse(not(WORD_CHARACTER).doesMatch("a"));
    assertFalse(not(WORD_CHARACTER).doesMatch("b"));
    assertFalse(not(WORD_CHARACTER).doesMatch("z"));
    assertFalse(not(WORD_CHARACTER).doesMatch("A"));
    assertFalse(not(WORD_CHARACTER).doesMatch("B"));
    assertFalse(not(WORD_CHARACTER).doesMatch("Z"));
    assertFalse(not(WORD_CHARACTER).doesMatch("0"));
    assertFalse(not(WORD_CHARACTER).doesMatch("1"));
    assertFalse(not(WORD_CHARACTER).doesMatch("5"));
    assertFalse(not(WORD_CHARACTER).doesMatch("9"));
    assertFalse(not(WORD_CHARACTER).doesMatch("_"));
    assertTrue(not(WORD_CHARACTER).doesMatch("-"));
    assertTrue(not(WORD_CHARACTER).doesMatch("."));
    assertTrue(not(WORD_CHARACTER).doesMatch("?"));

    assertFalse(not(WHITESPACE).doesMatch(" "));
    assertFalse(not(WHITESPACE).doesMatch("\t"));
    assertFalse(not(WHITESPACE).doesMatch("\n"));
    assertTrue(not(WHITESPACE).doesMatch("a"));
    assertTrue(not(WHITESPACE).doesMatch("0"));

    CharacterClass nc = not(character('c'));

    assertFalse(nc.doesMatch("c"));
    assertTrue(nc.doesMatch("d"));
    assertTrue(nc.doesMatch("b"));

    CharacterClass nr = not(range('a', 'd'));

    assertFalse(nr.doesMatch("a"));
    assertFalse(nr.doesMatch("b"));
    assertFalse(nr.doesMatch("c"));
    assertFalse(nr.doesMatch("d"));
    assertTrue(nr.doesMatch("e"));
    assertTrue(nr.doesMatch("A"));
    assertTrue(nr.doesMatch("."));
  }

  @Test
  public void testUnion() {
    CharacterClass uc = union('a', '\\', '.');

    assertTrue(uc.doesMatch("a"));
    assertTrue(uc.doesMatch("\\"));
    assertTrue(uc.doesMatch("."));
    assertFalse(uc.doesMatch("b"));
    assertFalse(uc.doesMatch("e"));

    CharacterClass uu = union(DIGIT, range('a', 'z'));

    assertTrue(uu.doesMatch("a"));
    assertTrue(uu.doesMatch("d"));
    assertTrue(uu.doesMatch("z"));
    assertTrue(uu.doesMatch("0"));
    assertTrue(uu.doesMatch("9"));
    assertFalse(uu.doesMatch("."));
    assertFalse(uu.doesMatch("\\"));

    CharacterClass single = union(DIGIT);
    assertTrue(single.doesMatch("0"));
    assertTrue(single.doesMatch("9"));
    assertFalse(single.doesMatch("b"));
    assertFalse(single.doesMatch("e"));
    assertFalse(single.doesMatch("."));
    assertFalse(single.doesMatch("\\"));
  }

  @Test
  public void testIntersection() {
    CharacterClass big = intersection(range('a', 'e'), range('c', 'j'), range('b', 'd'));

    assertFalse(big.doesMatch("a"));
    assertFalse(big.doesMatch("b"));
    assertTrue(big.doesMatch("c"));
    assertTrue(big.doesMatch("d"));
    assertFalse(big.doesMatch("e"));
    assertFalse(big.doesMatch("j"));
    assertFalse(big.doesMatch("k"));

    CharacterClass med = intersection(range('a', 'e'));

    assertTrue(med.doesMatch("a"));
    assertTrue(med.doesMatch("b"));
    assertTrue(med.doesMatch("c"));
    assertTrue(med.doesMatch("d"));
    assertTrue(med.doesMatch("e"));
    assertFalse(med.doesMatch("j"));
    assertFalse(med.doesMatch("k"));

    CharacterClass small = intersection();

    assertFalse(small.doesMatch("a"));
    assertFalse(small.doesMatch("b"));
    assertFalse(small.doesMatch("c"));
    assertFalse(small.doesMatch("d"));
    assertFalse(small.doesMatch("e"));
    assertFalse(small.doesMatch("j"));
    assertFalse(small.doesMatch("k"));
  }

  @Test
  public void testGrouping() {
    Regex r = anyAmount(range('a', 'z'));
    assertTrue(r.doesMatch("abcdefsajnidadnaowdnahiudonwjkdhbiua"));
    assertTrue(r.doesMatch(""));
    assertTrue(r.doesMatch("abcdefsahiudonwjk"));
  }

}
