package com.github.ermay12.regex;

/**
 * <p>This class represents a compiled character class. Instances of this class are immutable and
 * safe to be used by multiple concurrent threads.</p>
 *
 * <p>A character class can either be made out of a set of allowed characters, or out of a combination of
 *    other character classes. Note that all methods will escape characters if necessary automatically</p>
 *
 * <h3>Summary of Character Class Constructs</h3>
 * <table style="border: 0; border-collapse: collapse; border-spacing: 0;"><caption>Character Class, and what they match</caption>
 *
 * <tbody>
 *  <tr style="text-align: left">
 *    <th style="padding: 1px; text-align: left; background-color: #CCCCFF" id="construct">Construct</th>
 *    <th style="padding: 1px; text-align: left; background-color: #CCCCFF" id="matches">Matches</th>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *
 *  <tr style="text-align: left"><th style="padding: 1px;"colspan="2" id="basic">Basic Classes</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct basic"><i>character('x')</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">The character 'x'</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct basic"><i>union('x', 'a', ...)</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any of the characters 'x', 'a', ...</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct basic"><i>range('a', 'y')</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any of the characters whose ascii values lie in the range from 'a' to 'y', inclusive.</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *  <tr style="text-align: left"><th style="padding: 1px;"colspan="2" id="predef">Predefined Character Classes</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>WILDCARD</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any character</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>DIGIT</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any digit (0-9)</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>WORD_CHARACTER</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any word character (a-z, A-Z, 0-9 and _)</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>WHITESPACE</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any whitespace character ( ,\t,\n,\x0B,\f\r)</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *  <tr style="text-align: left"><th style="padding: 1px;"colspan="2" id="combo">Combination Character Classes</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct combo"><i>not(X)</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">All characters that are not found in
 *                                                                  the character class X</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct combo"><i>union(X1, X2, ...)</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any character that can be found in <b>any</b>
 *                                                                  of the character classes X1, X2, ...</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct combo"><i>intersection(X1, X2, ...)</i></td>
 *    <td style="padding: 1px; padding-left:5px;" headers="matches">Any character that can be found in <b>all</b>
 *                                                                  of the character classes X1, X2, ...</td>
 *  </tr>
 *
 *  </tbody>
 *  </table>
 */
public class CharacterClass extends Regex {
    /**
     * Creates a new character class from the given components
     * @param components the components to create the character class from
     */
    private CharacterClass(String... components) {
        super(components);
    }

    @Override
    String selfAsGrouped() {
        return getRawRegex();
    }

    /**
     * A character class representing any character possible
     */
    public static final CharacterClass WILDCARD = new CharacterClass(".");

    /**
     * A character class representing any digit. Equivalent to range('0', '9')
     */
    public static final CharacterClass DIGIT = new CharacterClass("\\d");

    /**
     * A character representing any character that is either alphanumeric or '_'. Equivalent to
     * union(range('a', 'z'), range('A', 'Z'), DIGIT, character('_'))
     */
    public static final CharacterClass WORD_CHARACTER = new CharacterClass("\\w");

    /**
     * Any whitespace character (e.g. one of  ,\t,\n,\x0B,\f, or \r
     */
    public static final CharacterClass WHITESPACE = new CharacterClass("\\s");

    /**
     * Returns a character class recognizing only the given character
     * @param c the character to recognize
     * @return a character class recognizing only the given character
     */
    public static CharacterClass character(char c) {
        return new CharacterClass(sanitized(c));
    }

    /**
     * Returns a character class containing all character whose ASCII values lie
     * between c1 and c2. If a character does not have an ascii value (e.g. unicode characters),
     * the raw bytes representing that character are used instead.
     * @param c1 The starting character of the range
     * @param c2 The ending character of the range
     * @return a character class containing all character whose ASCII values lies between c1 and c2
     */
    public static CharacterClass range(char c1, char c2) {
        return new CharacterClass("[", sanitized(c1), "-", sanitized(c2), "]");
    }

    /**
     * Returns a character class containing all characters that are not in the given character class
     * @param c A character class
     * @return a character class containing all characters that are not in the given character class
     */
    public static CharacterClass not(CharacterClass c) {
        if(c.equals(WILDCARD)){
            return new CharacterClass("[\\n]");
        }
        if(c.equals(DIGIT)){
            return new CharacterClass("\\D");
        }
        if(c.equals(WORD_CHARACTER)){
            return new CharacterClass("\\W");
        }
        if(c.equals(WHITESPACE)){
            return new CharacterClass("\\S");
        }

        if(c.getRawRegex().charAt(0) == '[') {
            return new CharacterClass("[^", c.getRawRegex().substring(1));
        } else {
            return new CharacterClass("[^", c.getRawRegex(), "]");
        }
    }

    /**
     * Returns a character class containing all of the characters passed in
     * @param choices the characters that the given character class should be made of
     * @return a character class containing all of the characters passed in
     */
    public static CharacterClass union(char... choices) {
        StringBuilder regex = new StringBuilder();
        regex.append("[");
        for(char choice : choices) {
            regex.append(sanitized(choice));
        }
        regex.append("]");
        return new CharacterClass(regex.toString());
    }

    /**
     * Returns a character class containing all characters found in any of the input character classes.
     * Equivalent to a union operation on sets.
     * @param choices the input character classes
     * @return a character class containing all characters found in any of the input character classes.
     */
    public static CharacterClass union(CharacterClass... choices) {
        assert(choices.length > 0); // Due to overload it isnt actually possible to pass this in
        if(choices.length > 1) {
            StringBuilder regex = new StringBuilder();
            regex.append("[");
            for (CharacterClass c : choices) {
                regex.append(c.getRawRegex());
            }
            regex.append("]");
            return new CharacterClass(regex.toString());
        } else {
            return choices[0];
        }
    }


    /**
     * Returns a character class containing all characters found in all of the input character classes.
     * Equivalent to an intersect operation on sets. If no arguments are passed in, returns a character
     * class that matches no characters.
     *
     * @param choices the input character classes
     * @return a character class containing all characters found in all of the input character classes.
     */
    public static CharacterClass intersection(CharacterClass... choices) {
        if(choices.length > 1) {
            StringBuilder regex = new StringBuilder();
            regex.append("[");
            for(int i = 0; i < choices.length; i++) {
                if(i != 0){
                    regex.append("&&");
                }
                regex.append(choices[i].getRawRegex());
            }
            regex.append("]");
            return new CharacterClass(regex.toString());
        } else if(choices.length == 1) {
            return choices[0];
        } else {
            return not(WILDCARD);
        }
    }
}
