package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CharacterClassUnitTests {
  @Test
  public void testBuiltin() {
    assertTrue(WILDCARD.toRegex().doesMatch("a"));
    assertTrue(WILDCARD.toRegex().doesMatch("\\"));
    assertTrue(WILDCARD.toRegex().doesMatch("d"));
    assertTrue(WILDCARD.toRegex().doesMatch("cab"));

    assertTrue(DIGIT.toRegex().doesMatch("0"));
    assertTrue(DIGIT.toRegex().doesMatch("1"));
    assertTrue(DIGIT.toRegex().doesMatch("5"));
    assertTrue(DIGIT.toRegex().doesMatch("9"));
    assertFalse(DIGIT.toRegex().doesMatch("a"));
    assertFalse(DIGIT.toRegex().doesMatch("z"));
    assertFalse(DIGIT.toRegex().doesMatch("\\"));

    assertTrue(WORD_CHARACTER.toRegex().doesMatch("a"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("b"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("z"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("A"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("B"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("Z"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("0"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("1"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("5"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("9"));
    assertTrue(WORD_CHARACTER.toRegex().doesMatch("_"));
    assertFalse(WORD_CHARACTER.toRegex().doesMatch("-"));
    assertFalse(WORD_CHARACTER.toRegex().doesMatch("."));
    assertFalse(WORD_CHARACTER.toRegex().doesMatch("?"));

    assertTrue(WHITESPACE.toRegex().doesMatch(" "));
    assertTrue(WHITESPACE.toRegex().doesMatch("\t"));
    assertTrue(WHITESPACE.toRegex().doesMatch("\n"));
    assertFalse(WHITESPACE.toRegex().doesMatch("a"));
    assertFalse(WHITESPACE.toRegex().doesMatch("0"));
  }

  @Test
  public void testCharacter() {
    CharacterClass c = character('c');

    assertTrue(c.toRegex().doesMatch("c"));
    assertFalse(c.toRegex().doesMatch("d"));
    assertFalse(c.toRegex().doesMatch("b"));
  }

  @Test
  public void testRange() {
    CharacterClass c = range('a', 'd');

    assertTrue(c.toRegex().doesMatch("a"));
    assertTrue(c.toRegex().doesMatch("b"));
    assertTrue(c.toRegex().doesMatch("c"));
    assertTrue(c.toRegex().doesMatch("d"));
    assertFalse(c.toRegex().doesMatch("e"));
    assertFalse(c.toRegex().doesMatch("A"));
    assertFalse(c.toRegex().doesMatch("."));
  }

  @Test
  public void testNot() {

    assertFalse(not(WILDCARD).toRegex().doesMatch("a"));
    assertFalse(not(WILDCARD).toRegex().doesMatch("\\"));
    assertFalse(not(WILDCARD).toRegex().doesMatch("d"));
    assertFalse(not(WILDCARD).toRegex().doesMatch("cab"));

    assertFalse(not(DIGIT).toRegex().doesMatch("0"));
    assertFalse(not(DIGIT).toRegex().doesMatch("1"));
    assertFalse(not(DIGIT).toRegex().doesMatch("5"));
    assertFalse(not(DIGIT).toRegex().doesMatch("9"));
    assertTrue(not(DIGIT).toRegex().doesMatch("a"));
    assertTrue(not(DIGIT).toRegex().doesMatch("z"));
    assertTrue(not(DIGIT).toRegex().doesMatch("\\"));

    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("a"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("b"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("z"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("A"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("B"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("Z"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("0"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("1"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("5"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("9"));
    assertFalse(not(WORD_CHARACTER).toRegex().doesMatch("_"));
    assertTrue(not(WORD_CHARACTER).toRegex().doesMatch("-"));
    assertTrue(not(WORD_CHARACTER).toRegex().doesMatch("."));
    assertTrue(not(WORD_CHARACTER).toRegex().doesMatch("?"));

    assertFalse(not(WHITESPACE).toRegex().doesMatch(" "));
    assertFalse(not(WHITESPACE).toRegex().doesMatch("\t"));
    assertFalse(not(WHITESPACE).toRegex().doesMatch("\n"));
    assertTrue(not(WHITESPACE).toRegex().doesMatch("a"));
    assertTrue(not(WHITESPACE).toRegex().doesMatch("0"));

    CharacterClass nc = not(character('c'));

    assertFalse(nc.toRegex().doesMatch("c"));
    assertTrue(nc.toRegex().doesMatch("d"));
    assertTrue(nc.toRegex().doesMatch("b"));

    CharacterClass nr = not(range('a', 'd'));

    assertFalse(nr.toRegex().doesMatch("a"));
    assertFalse(nr.toRegex().doesMatch("b"));
    assertFalse(nr.toRegex().doesMatch("c"));
    assertFalse(nr.toRegex().doesMatch("d"));
    assertTrue(nr.toRegex().doesMatch("e"));
    assertTrue(nr.toRegex().doesMatch("A"));
    assertTrue(nr.toRegex().doesMatch("."));
  }

  @Test
  public void testUnion() {
    CharacterClass uc = union('a', '\\', '.');

    assertTrue(uc.toRegex().doesMatch("a"));
    assertTrue(uc.toRegex().doesMatch("\\"));
    assertTrue(uc.toRegex().doesMatch("."));
    assertFalse(uc.toRegex().doesMatch("b"));
    assertFalse(uc.toRegex().doesMatch("e"));

    CharacterClass uu = union(DIGIT, range('a', 'z'));

    assertTrue(uu.toRegex().doesMatch("a"));
    assertTrue(uu.toRegex().doesMatch("d"));
    assertTrue(uu.toRegex().doesMatch("z"));
    assertTrue(uu.toRegex().doesMatch("0"));
    assertTrue(uu.toRegex().doesMatch("9"));
    assertFalse(uu.toRegex().doesMatch("."));
    assertFalse(uu.toRegex().doesMatch("\\"));

    CharacterClass single = union(DIGIT);
    assertTrue(single.toRegex().doesMatch("0"));
    assertTrue(single.toRegex().doesMatch("9"));
    assertFalse(single.toRegex().doesMatch("b"));
    assertFalse(single.toRegex().doesMatch("e"));
    assertFalse(single.toRegex().doesMatch("."));
    assertFalse(single.toRegex().doesMatch("\\"));
  }

  @Test
  public void testIntersection() {
    CharacterClass big = intersection(range('a', 'e'), range('c', 'j'), range('b', 'd'));

    assertFalse(big.toRegex().doesMatch("a"));
    assertFalse(big.toRegex().doesMatch("b"));
    assertTrue(big.toRegex().doesMatch("c"));
    assertTrue(big.toRegex().doesMatch("d"));
    assertFalse(big.toRegex().doesMatch("e"));
    assertFalse(big.toRegex().doesMatch("j"));
    assertFalse(big.toRegex().doesMatch("k"));

    CharacterClass med = intersection(range('a', 'e'));

    assertTrue(med.toRegex().doesMatch("a"));
    assertTrue(med.toRegex().doesMatch("b"));
    assertTrue(med.toRegex().doesMatch("c"));
    assertTrue(med.toRegex().doesMatch("d"));
    assertTrue(med.toRegex().doesMatch("e"));
    assertFalse(med.toRegex().doesMatch("j"));
    assertFalse(med.toRegex().doesMatch("k"));

    CharacterClass small = intersection();

    assertFalse(small.toRegex().doesMatch("a"));
    assertFalse(small.toRegex().doesMatch("b"));
    assertFalse(small.toRegex().doesMatch("c"));
    assertFalse(small.toRegex().doesMatch("d"));
    assertFalse(small.toRegex().doesMatch("e"));
    assertFalse(small.toRegex().doesMatch("j"));
    assertFalse(small.toRegex().doesMatch("k"));
  }

  @Test
  public void testGrouping() {
    Regex r = anyAmount(range('a', 'z'));
    assertTrue(r.doesMatch("abcdefsajnidadnaowdnahiudonwjkdhbiua"));
    assertTrue(r.doesMatch(""));
    assertTrue(r.doesMatch("abcdefsahiudonwjk"));
  }

}
