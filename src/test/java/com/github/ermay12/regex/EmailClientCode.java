package com.github.ermay12.regex;


import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;

public class EmailClientCode {
    public static void main(String[] args) {
        Regex regex = new Regex(
                LINE_START,
                capture(atLeastOne(CharacterClass.WILDCARD)),
                string("@"),
                capture(atLeastOne(CharacterClass.WILDCARD)),
                LINE_END
        );
        System.out.println(regex);
    }
}
