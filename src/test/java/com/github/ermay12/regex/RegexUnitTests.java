package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class RegexUnitTests {
    @Test
    public void testSingleCharacters() {
        Regex testRegex = single('a');
        assertTrue(testRegex.doesMatch("a"));
        assertFalse(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("baba"));
    }

    @Test
    public void testString() {
        Regex testRegex = string("al.\\a");
        assertTrue(testRegex.doesMatch("al.\\a"));
        assertFalse(testRegex.doesMatch("alp\\a"));
        assertTrue(testRegex.doesMatch("betal.\\a"));
    }

    @Test
    public void testRawRegex() {
        Regex testRegex = fromRawRegex("al.ha");
        assertTrue(testRegex.doesMatch("alpha"));
        assertFalse(testRegex.doesMatch("beta"));
    }

    @Test
    public void testBoundaries() {
        Regex testStart = concatenate(LINE_START, single('a'));

        assertTrue(testStart.doesMatch("a"));
        assertFalse(testStart.doesMatch("b"));
        assertFalse(testStart.doesMatch("baba"));

        Regex testEnd = concatenate(single('a'), LINE_END);

        assertTrue(testEnd.doesMatch("a"));
        assertFalse(testEnd.doesMatch("b"));
        assertTrue(testEnd.doesMatch("baba"));
        assertEquals("a", testEnd.firstMatch("baba").get().toString());


        Regex testWordBoundary = concatenate(WORD_BOUNDARY, single('a'));

        assertTrue(testWordBoundary.doesMatch("a"));
        assertTrue(testWordBoundary.doesMatch(" a"));
        assertFalse(testWordBoundary.doesMatch("ba"));

        Regex testNotWordBoundary = concatenate(NOT_WORD_BOUNDARY,single('a'));

        assertFalse(testNotWordBoundary.doesMatch("a"));
        assertFalse(testNotWordBoundary.doesMatch(" a"));
        assertTrue(testNotWordBoundary.doesMatch("ba"));
    }

    @Test
    public void testConcatenate() {
        Regex testRegex = concatenate(single('a'), single('b'));

        assertFalse(testRegex.doesMatch("a"));
        assertFalse(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("ab"));
        assertEquals("ab", testRegex.firstMatch("ab").get().matchString());
    }

    @Test
    public void testOneOf() {
        Regex testRegex = oneOf(single('a'), single('b'));

        assertTrue(testRegex.doesMatch("a"));
        assertTrue(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("ab"));

        Regex testOne = oneOf(single('a'));

        assertTrue(testOne.doesMatch("a"));
        assertFalse(testOne.doesMatch("b"));
        assertTrue(testOne.doesMatch("ab"));

        Regex testStrings = oneOf("a", "b");

        assertTrue(testStrings.doesMatch("a"));
        assertTrue(testStrings.doesMatch("b"));
        assertTrue(testStrings.doesMatch("ab"));

        Regex testOneString = oneOf("a");

        assertTrue(testOneString.doesMatch("a"));
        assertFalse(testOneString.doesMatch("b"));
        assertTrue(testOneString.doesMatch("ab"));
    }

    @Test
    public void testOptional() {
        Regex testRegex = concatenate(LINE_START, optional("ab"), string("ba"));

        assertTrue(testRegex.doesMatch("abba"));
        assertTrue(testRegex.doesMatch("ba"));
        assertFalse(testRegex.doesMatch("ababba"));


        Regex testSingle = concatenate(LINE_START, optional("a"), string("ba"));

        assertTrue(testSingle.doesMatch("aba"));
        assertTrue(testSingle.doesMatch("ba"));
        assertFalse(testSingle.doesMatch("bba"));

        Regex testNested = concatenate(LINE_START, optional(oneOf("a", "b")), string("ba"));

        assertTrue(testNested.doesMatch("aba"));
        assertTrue(testNested.doesMatch("bba"));
        assertTrue(testNested.doesMatch("ba"));
        assertFalse(testNested.doesMatch("cba"));
    }

    @Test
    public void testOptionalAlternateModes() {
        Regex testLazy = concatenate(LINE_START, capture(optional("ab", EvaluationMethod.LAZILY)), atLeastOne("ab"));

        assertTrue(testLazy.doesMatch("abab"));
        assertEquals("", testLazy.firstMatch("abab").get().group(1));

        Regex testPossessive = concatenate(LINE_START, capture(optional("ab", EvaluationMethod.POSSESSIVELY)), atLeastOne("ab"));
        assertTrue(testPossessive.doesMatch("abab"));
        assertFalse(testPossessive.doesMatch("ab"));
    }

    @Test
    public void testAnyAmount() {
        Regex testRegex = concatenate(LINE_START, anyAmount("ab"), LINE_END);

        assertTrue(testRegex.doesMatch(""));
        assertTrue(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("baba"));

        Regex testOneChar = concatenate(LINE_START, anyAmount("a"), LINE_END);

        assertTrue(testOneChar.doesMatch(""));
        assertTrue(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        Regex testNested = concatenate(LINE_START, anyAmount(oneOf("a", "b", "c")), LINE_END);

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
    public void testAnyAmountAlternateModes() {
        Regex testLazy = concatenate(LINE_START, capture(anyAmount("ab", EvaluationMethod.LAZILY)), atLeastOne("ab"));

        assertTrue(testLazy.doesMatch("ababab"));
        assertEquals("", testLazy.firstMatch("ababab").get().group(1));

        Regex testPossessive = concatenate(LINE_START, capture(anyAmount("ab", EvaluationMethod.POSSESSIVELY)), atLeastOne("ab"));
        assertFalse(testPossessive.doesMatch("ababab"));
        assertFalse(testPossessive.doesMatch("ab"));
    }

    @Test
    public void testAtLeastOne() {
        Regex testRegex = concatenate(LINE_START, atLeastOne("ab"), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertTrue(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("baba"));

        Regex testOneChar = concatenate(LINE_START, atLeastOne("a"), LINE_END);

        assertFalse(testOneChar.doesMatch(""));
        assertTrue(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        Regex testNested = concatenate(LINE_START, atLeastOne(oneOf("a", "b", "c")), LINE_END);

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
    public void testAtLeastOneAlternateModes() {
        Regex testLazy = concatenate(LINE_START, capture(atLeastOne("ab", EvaluationMethod.LAZILY)), atLeastOne("ab"));

        assertTrue(testLazy.doesMatch("ababab"));
        assertEquals("ab", testLazy.firstMatch("ababab").get().group(1));

        Regex testPossessive = concatenate(LINE_START, capture(atLeastOne("ab", EvaluationMethod.POSSESSIVELY)), atLeastOne("ab"));
        assertFalse(testPossessive.doesMatch("abababab"));
        assertFalse(testPossessive.doesMatch("abab"));
    }

    @Test
    public void testRepeat() {
        Regex testRegex = concatenate(LINE_START, repeat("ab", 2, 4), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertFalse(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertTrue(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertFalse(testRegex.doesMatch("ababababab"));

        Regex testOneChar = concatenate(LINE_START, repeat("a", 2, 4), LINE_END);

        assertFalse(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        Regex testNested = concatenate(LINE_START, repeat(oneOf("a", "b", "c"), 2, 4), LINE_END);

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
    public void testRepeatAlternateModes() {
        Regex testLazy = concatenate(LINE_START, capture(repeat("ab", 1, 3, EvaluationMethod.LAZILY)), atLeastOne("ab"));

        assertTrue(testLazy.doesMatch("abababab"));
        assertEquals("ab", testLazy.firstMatch("abababab").get().group(1));

        Regex testPossessive = concatenate(LINE_START, capture(repeat("ab", 1, 3, EvaluationMethod.POSSESSIVELY)), atLeastOne("ab"));
        assertTrue(testPossessive.doesMatch("abababab"));
        assertEquals("ababab", testPossessive.firstMatch("abababab").get().group(1));
        assertFalse(testPossessive.doesMatch("abab"));
    }

    @Test
    public void testRepeatExactly() {
        Regex testRegex = concatenate(LINE_START, repeatExactly("ab", 2), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertFalse(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertFalse(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertFalse(testRegex.doesMatch("ababababab"));

        Regex testOneChar = concatenate(LINE_START, repeatExactly("a", 2), LINE_END);

        assertFalse(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertFalse(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        Regex testNested = concatenate(LINE_START, repeatExactly(oneOf("a", "b", "c"), 2), LINE_END);

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
        Regex testRegex = concatenate(LINE_START, repeatAtLeast("ab", 2), LINE_END);

        assertFalse(testRegex.doesMatch(""));
        assertFalse(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertTrue(testRegex.doesMatch("ababab"));
        assertTrue(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertTrue(testRegex.doesMatch("ababababab"));

        Regex testOneChar = concatenate(LINE_START, repeatAtLeast("a", 2), LINE_END);

        assertFalse(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertTrue(testOneChar.doesMatch("aaaa"));
        assertTrue(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        Regex testNested = concatenate(LINE_START, repeatAtLeast(oneOf("a", "b", "c"), 2), LINE_END);

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
    public void testRepeatAtLeastAlternateModes() {
        Regex testLazy = concatenate(LINE_START, capture(repeatAtLeast("ab", 1, EvaluationMethod.LAZILY)), atLeastOne("ab"));

        assertTrue(testLazy.doesMatch("abababab"));
        assertEquals("ab", testLazy.firstMatch("abababab").get().group(1));

        Regex testPossessive = concatenate(LINE_START, capture(repeatAtLeast("ab", 1, EvaluationMethod.POSSESSIVELY)), atLeastOne("ab"));
        assertFalse(testPossessive.doesMatch("abababab"));
        assertFalse(testPossessive.doesMatch("abab"));
    }

    @Test
    public void testRepeatAtMost() {
        Regex testRegex = concatenate(LINE_START, repeatAtMost("ab", 2), LINE_END);

        assertTrue(testRegex.doesMatch(""));
        assertTrue(testRegex.doesMatch("ab"));
        assertTrue(testRegex.doesMatch("abab"));
        assertFalse(testRegex.doesMatch("ababab"));
        assertFalse(testRegex.doesMatch("abababab"));
        assertFalse(testRegex.doesMatch("baba"));
        assertFalse(testRegex.doesMatch("ababababab"));

        Regex testOneChar = concatenate(LINE_START, repeatAtMost("a", 2), LINE_END);

        assertTrue(testOneChar.doesMatch("a"));
        assertTrue(testOneChar.doesMatch("aa"));
        assertFalse(testOneChar.doesMatch("aaaa"));
        assertFalse(testOneChar.doesMatch("aaaaa"));
        assertFalse(testOneChar.doesMatch("baaaaa"));

        Regex testNested = concatenate(LINE_START, repeatAtMost(oneOf("a", "b", "c"), 2), LINE_END);

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
    public void testRepeatAtMostAlternateModes() {
        Regex testLazy = concatenate(LINE_START, capture(repeatAtMost("ab", 3, EvaluationMethod.LAZILY)), atLeastOne("ab"));

        assertTrue(testLazy.doesMatch("abababab"));
        assertEquals("", testLazy.firstMatch("abababab").get().group(1));

        Regex testPossessive = concatenate(LINE_START, capture(repeatAtMost("ab", 3, EvaluationMethod.POSSESSIVELY)), atLeastOne("ab"));
        assertTrue(testPossessive.doesMatch("abababab"));
        assertEquals("ababab", testPossessive.firstMatch("abababab").get().group(1));
        assertFalse(testPossessive.doesMatch("abab"));
    }

    @Test
    public void testLookahead() {
        Regex testRegex = concatenate(lookahead(single('a')), lookahead(single(WORD_CHARACTER)));

        assertTrue(testRegex.doesMatch("a"));
        assertFalse(testRegex.doesMatch("b"));
        assertTrue(testRegex.doesMatch("aba"));
    }

    @Test
    public void testNegativeLookahead() {
        Regex testRegex = concatenate(negativeLookahead(single('a')), lookahead(single(WORD_CHARACTER)));

        assertFalse(testRegex.doesMatch("a"));
        assertTrue(testRegex.doesMatch("b"));
        assertFalse(testRegex.doesMatch("-"));
    }


    @Test
    public void testLookbehind() {
        Regex testRegex = concatenate(WILDCARD, lookbehind(single('a')), lookbehind(single(WORD_CHARACTER)));

        assertTrue(testRegex.doesMatch("ab"));
        assertFalse(testRegex.doesMatch("bb"));
        assertTrue(testRegex.doesMatch("abv"));
    }

    @Test
    public void testNegativeLookbehind() {
        Regex testRegex = concatenate(WILDCARD, negativeLookbehind(single('a')), lookbehind(single(WORD_CHARACTER)), WILDCARD);

        assertTrue(testRegex.doesMatch("bd"));
        assertFalse(testRegex.doesMatch("ad"));
        assertFalse(testRegex.doesMatch("-a"));
    }

    @Test
    public void testNotMatched() {
        Regex testRegex = concatenate(WILDCARD, single('b'), WILDCARD);
        assertTrue(testRegex.firstMatch("abc").isPresent());
        assertTrue(testRegex.firstMatch("aac").isEmpty());
        assertTrue(testRegex.getMatch("abcabc", 1).isPresent());
        assertTrue(testRegex.getMatch("abcabc", 2).isEmpty());
    }

    @Test
    public void testRawRegexAdvanced() {
        Regex testRegex = Regex.fromRawRegex("(?<asd>(?<asd2>blah)nlah) i'm checking?<asd> " +
                                             "\\(?<asd>ha this isnt actually a capturing group\\)");

        assertTrue(testRegex.doesMatch("blahnlah i'm checking<asd> (<asd>ha this isnt actually a capturing group)"));
        assertTrue(testRegex.doesMatch("blahnlah i'm checkin<asd> (<asd>ha this isnt actually a capturing group)"));
        assertTrue(testRegex.doesMatch("blahnlah i'm checking<asd> <asd>ha this isnt actually a capturing group)"));

        RegexMatch m = testRegex.firstMatch("blahnlah i'm checking<asd> (<asd>ha this isnt actually a capturing group)").get();
        assertEquals("blahnlah", m.group(1));
        assertEquals("blah", m.group(2));

        Regex test2 = Regex.fromRawRegex("\\Qthis is also not (?<asd>a capturing group)\\E");
        assertTrue(test2.doesMatch("this is also not (?<asd>a capturing group)"));

        Regex test3 = Regex.fromRawRegex("\\Qthis is also not (?<asd>a capturing group)\\E");
        assertTrue(test3.doesMatch("this is also not (?<asd>a capturing group)"));

        Regex test4 = Regex.fromRawRegex("\\Qthis is also\\E not (?<asd>a capturing group)\\\\E");
        assertTrue(test4.doesMatch("this is also not a capturing group\\E"));

        Regex test5 = Regex.fromRawRegex("\\Qthis is also\\\\E not (?<asd>a capturing group)\\\\E");
        assertTrue(test5.doesMatch("this is also\\ not a capturing group\\E"));
    }
}
