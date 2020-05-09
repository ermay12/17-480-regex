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
        CapturingGroup badcap = capture(concatenate(DIGIT, DIGIT, DIGIT));
        Regex captureFun = concatenate(string("hi"), cap, string("foobar"));

        assertTrue(captureFun.doesMatch("hi123foobar"));
        assertEquals("hi123foobar", captureFun.firstMatch("hi123foobar").get().getGroup(0));
        assertEquals("123", captureFun.firstMatch("hi123foobar").get().getGroup(1));
        assertEquals("123", captureFun.firstMatch("hi123foobar").get().getGroup(cap));
        assertThrows(IllegalArgumentException.class, () -> {
            captureFun.firstMatch("hi123foobar").get().getGroup(badcap);
        });

        CapturingGroup cap2 = capture(anyAmount(DIGIT));
        Regex anyAmount = concatenate(string("hi"), cap2, string("foobar"));

        assertTrue(anyAmount.doesMatch("hi123foobar"));
        assertEquals("hi123foobar", anyAmount.firstMatch("hi123foobar").get().getGroup(0));
        assertEquals("123", anyAmount.firstMatch("hi123foobar").get().getGroup(1));
        assertEquals("123", anyAmount.firstMatch("hi123foobar").get().getGroup(cap2));

        CapturingGroup cap3 = capture(anyAmount(DIGIT));
        Regex lastOnly = concatenate(string("hi"), cap3, string("foobar"));

        assertTrue(lastOnly.doesMatch("hi123foobar"));
        assertEquals("hi123foobar", lastOnly.firstMatch("hi123foobar").get().getGroup(0));
        assertEquals("123", lastOnly.firstMatch("hi123foobar").get().getGroup(1));
        assertEquals("123", lastOnly.firstMatch("hi123foobar").get().getGroup(cap3));
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
        assertEquals("1h3 a", r.firstMatch("1h3 a").get().getGroup(0));
        assertEquals("1", r.firstMatch("1h3 a").get().getGroup(1));
        assertEquals("h", r.firstMatch("1h3 a").get().getGroup(2));
        assertEquals("3", r.firstMatch("1h3 a").get().getGroup(3));
        assertEquals(" ", r.firstMatch("1h3 a").get().getGroup(4));
        assertEquals("a", r.firstMatch("1h3 a").get().getGroup(5));

        assertEquals("1", r.firstMatch("1h3 a").get().getGroup(caps[0]));
        assertEquals("h", r.firstMatch("1h3 a").get().getGroup(caps[1]));
        assertEquals("3", r.firstMatch("1h3 a").get().getGroup(caps[2]));
        assertEquals(" ", r.firstMatch("1h3 a").get().getGroup(caps[3]));
        assertEquals("a", r.firstMatch("1h3 a").get().getGroup(caps[4]));
    }

    @Test
    public void smallRecursive() {
        CapturingGroup capInner1 = capture(anyAmount(DIGIT));
        CapturingGroup capInner2 = capture(oneOf("a", "b", "c"));
        CapturingGroup capInner3 = capture(anyAmount(DIGIT));
        CapturingGroup capMiddle1 = capture(capInner1, anyAmount(WHITESPACE), capInner2);
        CapturingGroup capMiddle2 = capture(capInner3, anyAmount(range('a', 'z')));
        CapturingGroup capOuter = capture(capMiddle1, string("-"), capMiddle2);

        Regex r = concatenate(LINE_START, capOuter, LINE_END);
        assertTrue(r.doesMatch("123 a-41abba"));
        RegexMatch m = r.firstMatch("123 a-41abba").get();
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
        RegexMatch m1 = r1.firstMatch("123 a-41abba").get();
        assertEquals("123", m1.getGroup(0));
        assertEquals("1", m1.getGroup(1));
        assertEquals("2", m1.getGroup(2));
        assertEquals("3", m1.getGroup(3));
        assertEquals("3", m1.getGroup(c1));

        CapturingGroup capInner1 = capture(anyAmount(DIGIT));
        CapturingGroup capInner2 = capture(oneOf("a", "b", "c"));
        CapturingGroup capMiddle1 = capture(capInner1, anyAmount(WHITESPACE), capInner2);
        CapturingGroup capMiddle2 = capture(capInner1, anyAmount(range('a', 'z')));
        CapturingGroup capOuter = capture(capMiddle1, string("-"), capMiddle2);

        Regex r = concatenate(LINE_START, capOuter, LINE_END);
        assertTrue(r.doesMatch("123 a-41abba"));
        RegexMatch m = r.firstMatch("123 a-41abba").get();
        assertEquals("123 a-41abba", m.getGroup(0));
        assertEquals("123 a-41abba", m.getGroup(1));
        assertEquals("123 a", m.getGroup(2));
        assertEquals("123", m.getGroup(3));
        assertEquals("a", m.getGroup(4));
        assertEquals("41abba", m.getGroup(5));
        assertEquals("41", m.getGroup(6));

        assertEquals("123 a-41abba", m.getGroup(capOuter));
        assertEquals("123 a", m.getGroup(capMiddle1));
        assertEquals("a", m.getGroup(capInner2));
        assertEquals("41abba", m.getGroup(capMiddle2));
        assertEquals("41", m.getGroup(capInner1));
    }

    @Test
    public void simpleBackReference() {
        CapturingGroup cap = capture(DIGIT, DIGIT, DIGIT);
        Regex captureFun = concatenate(string("hi"), cap, string("foobar"), backReference(cap));

        assertTrue(captureFun.doesMatch("hi123foobar123"));
        assertTrue(captureFun.doesMatch("hi154foobar154"));
        assertFalse(captureFun.doesMatch("hi512foobar123"));

        Regex captureFun2 = concatenate(string("hi"), cap, string("foobar"), backReference(1));

        assertTrue(captureFun2.doesMatch("hi123foobar123"));
        assertTrue(captureFun2.doesMatch("hi154foobar154"));
        assertFalse(captureFun2.doesMatch("hi512foobar123"));

        CapturingGroup cap3 = capture(DIGIT);
        Regex lastOnly = concatenate(string("hi"), anyAmount(cap3), string("foobar"), backReference(cap3));

        assertTrue(lastOnly.doesMatch("hi123foobar3"));
        assertFalse(lastOnly.doesMatch("hi123foobar1"));
        assertFalse(lastOnly.doesMatch("hi123foobar2"));

        CapturingGroup cap4 = capture(DIGIT);
        Regex many = concatenate(string("hi"), anyAmount(cap4), string("foobar"), backReference(cap4), backReference(cap4), backReference(cap4));

        assertTrue(many.doesMatch("hi123foobar333"));
        assertFalse(many.doesMatch("hi123foobar133"));
        assertFalse(many.doesMatch("hi123foobar233"));
    }

    @Test
    public void recursiveBackreferences() {
        CapturingGroup capInner1 = capture(anyAmount(DIGIT));
        CapturingGroup capInner2 = capture(oneOf("a", "b", "c"));
        CapturingGroup capInner3 = capture(anyAmount(DIGIT));
        CapturingGroup capMiddle1 = capture(capInner1, anyAmount(WHITESPACE), capInner2);
        CapturingGroup capMiddle2 = capture(capInner3, anyAmount(range('a', 'z')));
        CapturingGroup capOuter = capture(capMiddle1, string("-"),  backReference(capInner1), string("-"), capMiddle2, backReference(capInner3));

        Regex r = concatenate(LINE_START, capOuter, LINE_END);
        assertFalse(r.doesMatch("123 a-41abba"));
        assertTrue(r.doesMatch("123 a-123-41abba41"));
        assertFalse(r.doesMatch("13 a-123-41abba41"));
        assertFalse(r.doesMatch("123 a-123-41abba4"));
        assertTrue(r.doesMatch("41 a-41-123abba123"));
    }

    @Test
    public void reuseBackreferences() {
        CapturingGroup capInner1 = capture(anyAmount(DIGIT));
        CapturingGroup capInner2 = capture(oneOf("a", "b", "c"));
        CapturingGroup capMiddle1 = capture(capInner1, anyAmount(WHITESPACE), capInner2);
        CapturingGroup capMiddle2 = capture(capInner1, anyAmount(range('a', 'z')));
        CapturingGroup capOuter = capture(capMiddle1, string("-"), capMiddle2);

        Regex r = concatenate(LINE_START, capOuter, LINE_END);
        assertTrue(r.doesMatch("123 a-41abba"));
        RegexMatch m = r.firstMatch("123 a-41abba").get();
        assertEquals("123 a-41abba", m.getGroup(0));
        assertEquals("123 a-41abba", m.getGroup(1));
        assertEquals("123 a", m.getGroup(2));
        assertEquals("123", m.getGroup(3));
        assertEquals("a", m.getGroup(4));
        assertEquals("41abba", m.getGroup(5));
        assertEquals("41", m.getGroup(6));

        assertEquals("123 a-41abba", m.getGroup(capOuter));
        assertEquals("123 a", m.getGroup(capMiddle1));
        assertEquals("a", m.getGroup(capInner2));
        assertEquals("41abba", m.getGroup(capMiddle2));
        assertEquals("41", m.getGroup(capInner1));
    }

    @Test
    public void illegalBackreferences() {
        CapturingGroup cap1 = capture(repeat(DIGIT, 2, 4));
        CapturingGroup cap2 = capture(anyAmount(DIGIT));

        Regex p1 = concatenate(cap1, backReference(cap1));

        assertThrows(IllegalArgumentException.class, () -> {
            Regex illegal = concatenate(p1, cap1);
        });
    }
}
