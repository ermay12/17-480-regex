package com.github.ermay12.regex;

import org.junit.Test;


import static com.github.ermay12.regex.RegexBuilder.StaticHelpers.*;
import static org.junit.Assert.assertEquals;

public class PasswordTest {
    @Test
    public void testPassword() {

        //Without importing everything
        Regex regex =
            new Regex(
                Regex.LINESTART,
                Regex.lookahead(Charset.WILDCARD.anyAmount(), Charset.range('a', 'z')),
                Regex.lookahead(Charset.WILDCARD.anyAmount(), Charset.range('A', 'Z')),
                Regex.lookahead(Charset.WILDCARD.anyAmount(), Charset.DIGIT),
                Regex.lookahead(Charset.WILDCARD.anyAmount(), Charset.without(Charset.WORD_CHAR)),
                Charset.WILDCARD.repeatAtLeast(8),
                Regex.LINEEND
            );

        // importing everything
        import static com.github.ermay12.regex.Regex.*;
        import static com.github.ermay12.regex.Charset.*;
        import com.github.ermay12.regex.Regex;
        import com.github.ermay12.regex.Charset;


        Regex regex =
            new Regex(
                LINESTART,
                lookahead(WILDCARD.anyAmount(), range('a', 'z')),
                lookahead(WILDCARD.anyAmount(), range('A', 'Z')),
                lookahead(WILDCARD.anyAmount(), DIGIT),
                lookahead(WILDCARD.anyAmount(), Charset.without(WORD_CHAR)),
                WILDCARD.repeatAtLeast(8),
                LINEEND
            );

        Regex regex =
                startLine()
                .lookahead(anyAmount(wildcard()).single(range('a', 'z')))
                .lookahead(anyAmount(wildcard()).single(range('A', 'Z')))
                .lookahead(anyAmount(wildcard()).single(digit()))
                .lookahead(anyAmount(wildcard()).single(not(wordCharacter())))
                .repeatAtLeast(wildcard(), 8)
                .endLine()
                .build();
        assertEquals("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w]).{8,}$",
                     regex.toString());
    }
}
