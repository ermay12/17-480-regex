package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class RegexUnitTests {
    @Test
    public void testSingleCharacters() {
        RegularExpression testRegex = single('a');
        assertTrue(testRegex.doesMatch("a"));
        assertFalse(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("baba"));
    }

    @Test
    public void testString() {
        RegularExpression testRegex = string("al.\\a");
        assertTrue(testRegex.doesMatch("al.\\a"));
        assertFalse(testRegex.doesMatch("alp\\a"));
        assertTrue(testRegex.doesMatch("betal.\\a"));
    }

    @Test
    public void testRawRegex() {
        RegularExpression testRegex = fromRawRegex("al.ha");
        assertTrue(testRegex.doesMatch("alpha"));
        assertFalse(testRegex.doesMatch("beta"));
    }

    @Test
    public void testBoundaries() {
        RegularExpression testStart = concatenate(LINE_START, single('a'));

        assertTrue(testStart.doesMatch("a"));
        assertFalse(testStart.doesMatch("b"));
        assertFalse(testStart.doesMatch("baba"));


        RegularExpression testEnd = concatenate(single('a'), LINE_END);

        assertTrue(testEnd.doesMatch("a"));
        assertFalse(testEnd.doesMatch("b"));
        assertTrue(testEnd.doesMatch("baba"));
    }

    @Test
    public void testConcatenate() {
        RegularExpression testRegex = concatenate(single('a'), single('b'));

        assertFalse(testRegex.doesMatch("a"));
        assertFalse(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("ab"));
    }

    @Test
    public void testOneOf() {
        RegularExpression testRegex = oneOf(single('a'), single('b'));

        assertTrue(testRegex.doesMatch("a"));
        assertTrue(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("ab"));

        RegularExpression testOne = oneOf(single('a'));

        assertTrue(testOne.doesMatch("a"));
        assertFalse(testOne.doesMatch("b"));
        assertTrue(testOne.doesMatch("ab"));

        RegularExpression testStrings = oneOf("a", "b");

        assertTrue(testStrings.doesMatch("a"));
        assertTrue(testStrings.doesMatch("b"));
        assertTrue(testStrings.doesMatch("ab"));

        RegularExpression testOneString = oneOf("a");

        assertTrue(testOneString.doesMatch("a"));
        assertFalse(testOneString.doesMatch("b"));
        assertTrue(testOneString.doesMatch("ab"));
    }

    @Test
    public void testOptional() {
        RegularExpression testRegex = concatenate(LINE_START, optional("ab"), string("ba"));

        assertTrue(testRegex.doesMatch("abba"));
        assertTrue(testRegex.doesMatch("ba"));
        assertFalse(testRegex.doesMatch("ababba"));


        RegularExpression testSingle = concatenate(LINE_START, optional("a"), string("ba"));

        assertTrue(testSingle.doesMatch("aba"));
        assertTrue(testSingle.doesMatch("ba"));
        assertFalse(testSingle.doesMatch("bba"));

        RegularExpression testNested = concatenate(LINE_START, optional(oneOf("a", "b")), string("ba"));

        assertTrue(testNested.doesMatch("aba"));
        assertTrue(testNested.doesMatch("bba"));
        assertTrue(testNested.doesMatch("ba"));
        assertFalse(testNested.doesMatch("cba"));
    }

    @Test
    public void testAnyAmount() {
        RegularExpression testRegex = concatenate(LINE_START, anyAmount("ab"), LINE_END);

        assertTrue(testRegex.doesMatch(""));
        assertTrue(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("baba"));

        RegularExpression testOneChar = concatenate(LINE_START, anyAmount("a"), LINE_END);

        assertTrue(testOneChar.doesMatch(""));
        assertTrue(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        RegularExpression testNested = concatenate(LINE_START, anyAmount(oneOf("a", "b", "c")), LINE_END);

        assertTrue(testNested.doesMatch(""));
        assertTrue(testNested.doesMatch("a"));
        assertTrue(testNested.doesMatch("b"));
        assertTrue(testNested.doesMatch("c"));
        assertTrue(testNested.doesMatch("bab"));
        assertTrue(testNested.doesMatch("cab"));
        assertTrue(testNested.doesMatch("cabbaa"));
        assertTrue(testNested.doesMatch("bababababac"));
        assertFalse(testNested.doesMatch("dbababababac"));
    }

    @Test
    public void testAtLeastOne() {
        RegularExpression testRegex = concatenate(LINE_START, atLeastOne("ab"), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertTrue(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("baba"));

        RegularExpression testOneChar = concatenate(LINE_START, atLeastOne("a"), LINE_END);

        assertFalse(testOneChar.doesMatch(""));
        assertTrue(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        RegularExpression testNested = concatenate(LINE_START, atLeastOne(oneOf("a", "b", "c")), LINE_END);

        assertFalse(testNested.doesMatch(""));
        assertTrue(testNested.doesMatch("a"));
        assertTrue(testNested.doesMatch("b"));
        assertTrue(testNested.doesMatch("c"));
        assertTrue(testNested.doesMatch("bab"));
        assertTrue(testNested.doesMatch("cab"));
        assertTrue(testNested.doesMatch("cabbaa"));
        assertTrue(testNested.doesMatch("bababababac"));
        assertFalse(testNested.doesMatch("dbababababac"));
    }

    @Test
    public void testRepeat() {
        RegularExpression testRegex = concatenate(LINE_START, repeat("ab", 2, 4), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertFalse(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertTrue(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertFalse(testRegex.doesMatch("ababababab"));

        RegularExpression testOneChar = concatenate(LINE_START, repeat("a", 2, 4), LINE_END);

        assertFalse(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        RegularExpression testNested = concatenate(LINE_START, repeat(oneOf("a", "b", "c"), 2, 4), LINE_END);

        assertFalse(testNested.doesMatch(""));
        assertFalse(testNested.doesMatch("a"));
        assertFalse(testNested.doesMatch("b"));
        assertFalse(testNested.doesMatch("c"));
        assertTrue(testNested.doesMatch("ba"));
        assertTrue(testNested.doesMatch("cab"));
        assertTrue(testNested.doesMatch("baba"));
        assertTrue(testNested.doesMatch("bacb"));
        assertFalse(testNested.doesMatch("cabbaa"));
        assertFalse(testNested.doesMatch("bababababac"));
        assertFalse(testNested.doesMatch("dbababababac"));
    }

    @Test
    public void testRepeatExactly() {
        RegularExpression testRegex = concatenate(LINE_START, repeatExactly("ab", 2), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertFalse(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertFalse(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertFalse(testRegex.doesMatch("ababababab"));

        RegularExpression testOneChar = concatenate(LINE_START, repeatExactly("a", 2), LINE_END);

        assertFalse(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertFalse(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        RegularExpression testNested = concatenate(LINE_START, repeatExactly(oneOf("a", "b", "c"), 2), LINE_END);

        assertFalse(testNested.doesMatch(""));
        assertFalse(testNested.doesMatch("a"));
        assertFalse(testNested.doesMatch("b"));
        assertFalse(testNested.doesMatch("c"));
        assertTrue(testNested.doesMatch("ba"));
        assertTrue(testNested.doesMatch("cb"));
        assertTrue(testNested.doesMatch("ac"));
        assertFalse(testNested.doesMatch("cab"));
        assertFalse(testNested.doesMatch("baba"));
        assertFalse(testNested.doesMatch("bacb"));
        assertFalse(testNested.doesMatch("cabbaa"));
        assertFalse(testNested.doesMatch("bababababac"));
        assertFalse(testNested.doesMatch("dbababababac"));
    }

    @Test
    public void testRepeatAtLeast() {
        RegularExpression testRegex = concatenate(LINE_START, repeatAtLeast("ab", 2), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertFalse(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertTrue(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertTrue(testRegex.doesMatch("ababababab"));

        RegularExpression testOneChar = concatenate(LINE_START, repeatAtLeast("a", 2), LINE_END);

        assertFalse(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertTrue(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        RegularExpression testNested = concatenate(LINE_START, repeatAtLeast(oneOf("a", "b", "c"), 2), LINE_END);

        assertFalse(testNested.doesMatch(""));
        assertFalse(testNested.doesMatch("a"));
        assertFalse(testNested.doesMatch("b"));
        assertFalse(testNested.doesMatch("c"));
        assertTrue(testNested.doesMatch("ba"));
        assertTrue(testNested.doesMatch("cb"));
        assertTrue(testNested.doesMatch("ac"));
        assertTrue(testNested.doesMatch("cab"));
        assertTrue(testNested.doesMatch("baba"));
        assertTrue(testNested.doesMatch("bacb"));
        assertTrue(testNested.doesMatch("cabbaa"));
        assertTrue(testNested.doesMatch("bababababac"));
        assertFalse(testNested.doesMatch("dbababababac"));
    }

    @Test
    public void testRepeatAtMost() {
        RegularExpression testRegex = concatenate(LINE_START, repeatAtMost("ab", 2), LINE_END);

        assertTrue(testRegex.doesMatch(""));
        assertTrue(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertFalse(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertFalse(testRegex.doesMatch("ababababab"));

        RegularExpression testOneChar = concatenate(LINE_START, repeatAtMost("a", 2), LINE_END);

        assertTrue(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertFalse(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        RegularExpression testNested = concatenate(LINE_START, repeatAtMost(oneOf("a", "b", "c"), 2), LINE_END);

        assertTrue(testNested.doesMatch(""));
        assertTrue(testNested.doesMatch("a"));
        assertTrue(testNested.doesMatch("b"));
        assertTrue(testNested.doesMatch("c"));
        assertTrue(testNested.doesMatch("ba"));
        assertTrue(testNested.doesMatch("cb"));
        assertTrue(testNested.doesMatch("ac"));
        assertFalse(testNested.doesMatch("cab"));
        assertFalse(testNested.doesMatch("baba"));
        assertFalse(testNested.doesMatch("bacb"));
        assertFalse(testNested.doesMatch("cabbaa"));
        assertFalse(testNested.doesMatch("bababababac"));
        assertFalse(testNested.doesMatch("dbababababac"));
    }

    @Test
    public void testLookahead() {
        RegularExpression testRegex = concatenate(lookahead(single('a')), lookahead(single(WORD_CHARACTER)));

        assertTrue(testRegex.doesMatch("a"));
        assertFalse(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("aba"));
    }

    @Test
    public void testNegativeLookahead() {
        RegularExpression testRegex = concatenate(negativeLookahead(single('a')), lookahead(single(WORD_CHARACTER)));

        assertFalse(testRegex.doesMatch("a"));
        assertTrue(testRegex.doesMatch("b"));
        assertFalse(testRegex.doesMatch("-"));
    }


    @Test
    public void testLookbehind() {
        RegularExpression testRegex = concatenate(WILDCARD, lookbehind(single('a')), lookbehind(single(WORD_CHARACTER)));

        assertTrue(testRegex.doesMatch("ab"));
        assertFalse(testRegex.doesMatch("bb"));
        assertTrue(testRegex.doesMatch("abv"));
    }

    @Test
    public void testNegativeLookbehind() {
        RegularExpression testRegex = concatenate(WILDCARD, negativeLookbehind(single('a')), lookbehind(single(WORD_CHARACTER)), WILDCARD);

        assertTrue(testRegex.doesMatch("bd"));
        assertFalse(testRegex.doesMatch("ad"));
        assertFalse(testRegex.doesMatch("-a"));
    }
}
