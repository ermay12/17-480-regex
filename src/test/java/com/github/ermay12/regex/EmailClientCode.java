package com.github.ermay12.regex;


import org.junit.Test;

import java.util.Optional;

import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static com.github.ermay12.regex.CharacterClass.*;

public class EmailClientCode {
    @Test
    public void checkEmail() {
        CapturingGroup nameGroup = CapturingGroup.capture(anyAmount(not(character('@'))));
        CapturingGroup domainGroup = CapturingGroup.capture(anyAmount(not(character('.'))),
                string("."), anyAmount(WILDCARD));

        Regex regex = concatenate(nameGroup, string("@"), domainGroup);

        String input = "hello@example.com";

        Optional<RegexMatch> result = regex.firstMatch(input);
        if (result.isPresent()) {
            System.out.println("Name: " + result.get().getGroup(nameGroup));
            System.out.println("Domain: " + result.get().getGroup(domainGroup));
        }
        else {
            System.out.println("Invalid email :(");
        }
    }
}
