package com.github.ermay12.regex;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CapturingTests {
    @Test
    public void simpleCapture() {
        CapturingGroup cap = capture(concatenate(DIGIT, DIGIT, DIGIT));
        Regex captureFun = concatenate(string("hi"), cap, string("foobar"));

        assertTrue(captureFun.doesMatch("hi123foobar"));
        assertEquals("hi123foobar", captureFun.firstMatch("hi123foobar").getGroup(0));
        assertEquals("123", captureFun.firstMatch("hi123foobar").getGroup(1));
        assertEquals("123", captureFun.firstMatch("hi123foobar").getGroup(cap));

        CapturingGroup cap2 = capture(anyAmount(DIGIT));
        Regex anyAmount = concatenate(string("hi"), cap2, string("foobar"));

        assertTrue(anyAmount.doesMatch("hi123foobar"));
        assertEquals("hi123foobar", anyAmount.firstMatch("hi123foobar").getGroup(0));
        assertEquals("123", anyAmount.firstMatch("hi123foobar").getGroup(1));
        assertEquals("123", anyAmount.firstMatch("hi123foobar").getGroup(cap2));

        CapturingGroup cap3 = capture(anyAmount(DIGIT));
        Regex lastOnly = concatenate(string("hi"), cap3, string("foobar"));

        assertTrue(lastOnly.doesMatch("hi123foobar"));
        assertEquals("hi123foobar", lastOnly.firstMatch("hi123foobar").getGroup(0));
        assertEquals("123", lastOnly.firstMatch("hi123foobar").getGroup(1));
        assertEquals("123", lastOnly.firstMatch("hi123foobar").getGroup(cap3));
    }

    @Test
    public void manyCaptures() {
        CapturingGroup[] caps = new CapturingGroup[5];
        caps[0] = capture(DIGIT);
        caps[1] = capture(WORD_CHARACTER);
        caps[2] = capture(DIGIT);
        caps[3] = capture(WHITESPACE);
        caps[4] = capture(not(DIGIT));

        Regex r = concatenate(caps[0], caps[1], caps[2], caps[3], caps[4]);

        assertTrue(r.doesMatch("1h3 a"));
        assertEquals("1h3 a", r.firstMatch("1h3 a").getGroup(0));
        assertEquals("1", r.firstMatch("1h3 a").getGroup(1));
        assertEquals("h", r.firstMatch("1h3 a").getGroup(2));
        assertEquals("3", r.firstMatch("1h3 a").getGroup(3));
        assertEquals(" ", r.firstMatch("1h3 a").getGroup(4));
        assertEquals("a", r.firstMatch("1h3 a").getGroup(5));

        assertEquals("1", r.firstMatch("1h3 a").getGroup(caps[0]));
        assertEquals("h", r.firstMatch("1h3 a").getGroup(caps[1]));
        assertEquals("3", r.firstMatch("1h3 a").getGroup(caps[2]));
        assertEquals(" ", r.firstMatch("1h3 a").getGroup(caps[3]));
        assertEquals("a", r.firstMatch("1h3 a").getGroup(caps[4]));
    }

    @Test
    public void smallRecursive() {
        CapturingGroup capInner1 = capture(anyAmount(DIGIT));
        CapturingGroup capInner2 = capture(oneOf("a", "b", "c"));
        CapturingGroup capInner3 = capture(anyAmount(DIGIT));
        CapturingGroup capMiddle1 = capture(concatenate(capInner1, anyAmount(WHITESPACE), capInner2));
        CapturingGroup capMiddle2 = capture(concatenate(capInner3, anyAmount(range('a', 'z'))));
        CapturingGroup capOuter = capture(concatenate(capMiddle1, string("-"), capMiddle2));

        Regex r = concatenate(LINE_START, capOuter, LINE_END);
        assertTrue(r.doesMatch("123 a-41abba"));
        RegexMatch m = r.firstMatch("123 a-41abba");
        assertEquals("123 a-41abba", m.getGroup(0));
        assertEquals("123 a-41abba", m.getGroup(1));
        assertEquals("123 a", m.getGroup(2));
        assertEquals("123", m.getGroup(3));
        assertEquals("a", m.getGroup(4));
        assertEquals("41abba", m.getGroup(5));
        assertEquals("41", m.getGroup(6));

        assertEquals("123 a-41abba", m.getGroup(capOuter));
        assertEquals("123 a", m.getGroup(capMiddle1));
        assertEquals("123", m.getGroup(capInner1));
        assertEquals("a", m.getGroup(capInner2));
        assertEquals("41abba", m.getGroup(capMiddle2));
        assertEquals("41", m.getGroup(capInner3));
    }

    @Test
    public void onlyUseLast() {
        CapturingGroup c1 = capture(DIGIT);

        Regex r1 = concatenate(c1, c1, c1);
        assertTrue(r1.doesMatch("123"));
        RegexMatch m1 = r1.firstMatch("123 a-41abba");
        assertEquals("123", m1.getGroup(0));
        assertEquals("3", m1.getGroup(1));
        assertEquals("3", m1.getGroup(c1));

        CapturingGroup capInner1 = capture(anyAmount(DIGIT));
        CapturingGroup capInner2 = capture(oneOf("a", "b", "c"));
        CapturingGroup capMiddle1 = capture(concatenate(capInner1, anyAmount(WHITESPACE), capInner2));
        CapturingGroup capMiddle2 = capture(concatenate(capInner1, anyAmount(range('a', 'z'))));
        CapturingGroup capOuter = capture(concatenate(capMiddle1, string("-"), capMiddle2));

        Regex r = concatenate(LINE_START, capOuter, LINE_END);
        assertTrue(r.doesMatch("123 a-41abba"));
        RegexMatch m = r.firstMatch("123 a-41abba");
        assertEquals("123 a-41abba", m.getGroup(0));
        assertEquals("123 a-41abba", m.getGroup(1));
        assertEquals("123 a", m.getGroup(2));
        assertEquals("a", m.getGroup(3));
        assertEquals("41abba", m.getGroup(4));
        assertEquals("41", m.getGroup(5));

        assertEquals("123 a-41abba", m.getGroup(capOuter));
        assertEquals("123 a", m.getGroup(capMiddle1));
        assertEquals("a", m.getGroup(capInner2));
        assertEquals("41abba", m.getGroup(capMiddle2));
        assertEquals("41", m.getGroup(capInner1));
    }
}
