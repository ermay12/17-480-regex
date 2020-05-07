/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.ermay12.regex;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * This class represents a compiled Regular expression. Instances of this class are immutable and
 * safe to be used by multiple concurrent threads.
 *
 * <h3>Summary of Regular Expression Constructs</h3>
 *
 * <table style="border: 0; border-collapse: collapse; border-spacing: 0;">
 * <caption>Regular Expression Constructs</caption>
 * <tbody>
 *  <tr style="text-align: left">
 *    <th style="padding: 1px; text-align: left; background-color: #CCCCFF" id="construct">Construct</th>
 *    <th style="padding: 1px; text-align: left; background-color: #CCCCFF" id="matches">Matches</th>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *
 *  <tr style="text-align: left"><th colspan="2" id="characters">Characters</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct characters"><i>single('x')</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">The character 'x'. Note that this will escape 'x' if necessary
 *                          (i.e. if it is a special regex character)</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct characters"><i>string("ab.")</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">The string 'ab.'. Note that this will escape any characters if necessary
 *                          (i.e. if it is a special regex character, such as the '.' in this example)</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct characters"><i>fromRawRegex("ab.")</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">The regex 'ab.'. Note that this will <b>NOT</b> escape any characters
 *                          (i.e. the '.' in this example)</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *
 *  <tr style="text-align: left"><th colspan="2" id="classes">Character Classes</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct classes"><i>single(CharacterClass)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">The same thing that the given character class matches. See {@link CharacterClass}</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *
 *  <tr style="text-align: left"><th colspan="2" id="predef">Predefined Character Classes</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>CharacterClass.WILDCARD</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">Any character. See {@link CharacterClass#WILDCARD}</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>CharacterClass.DIGIT</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">Any digit (0-9). See {@link CharacterClass#DIGIT}</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>CharacterClass.WORD_CHARACTER</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">Any word character (a-z, A-Z, 0-9 and _). See {@link CharacterClass#WORD_CHARACTER}</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct predef"><i>CharacterClass.WHITESPACE</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">Any whitespace character ( ,\t,\n,\x0B,\f\r). See {@link CharacterClass#WHITESPACE}</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *
 *  <tr style="text-align: left"><th colspan="2" id="bound">Boundaries</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct bound"><i>LINE_START</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">The start of a line. If multi-line mode is off, then this matches the start of input</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct bound"><i>LINE_END</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">The end of a line. If multi-line mode is off, then this matches the end of input</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *
 *  <tr style="text-align: left"><th colspan="2" id="quant">Quantifiers</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>optional(X)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, either once or not at all</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>anyAmount(X)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, zero or more times</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>atLeastOne(X)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, one or more times</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeatExactly(X, amount)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, repeated exactly amount times</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeatAtLeast(X, min)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, min or more times</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeatAtMost(X, max)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, repeated at most max times</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeat(X, min, max)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, repeated at least min and at most max times</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *  <tr style="text-align: left"><th colspan="2" id="logical">Logical Operators</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct logical"><i>new Regex(X1, X2, ...)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X1 followed by X2 followed by ...</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct logical"><i>concatenate(X1, X2, ...)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X1 followed by X2 followed by ...</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct logical"><i>oneOf(X1, X2, ...)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">either X1 or X2 or ...</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *  <tr style="text-align: left"><th colspan="2" id="back">Back References</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct back"><i>backReference(i)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">What the i'th capturing group matched</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct back"><i>backReference(group)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">What the capturing group passed in matched</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>
 *  <tr style="text-align: left"><th colspan="2" id="lookaround">Lookaround</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct capture"><i>lookahead(X)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">asserts that the rest of the string matches X, but does not consume any characters</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct capture"><i>negativeLookahead(X)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">asserts that the rest of the string does not match X, but does not consume any characters</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct capture"><i>lookbehind(X)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">asserts that the precending bit of the string matches X, but does not consume any characters</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct capture"><i>negativeLookbehind(X)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">asserts that the precending bit of the string does not match X, but does not consume any characters</td>
 *  </tr>
 *  </tbody>
 * </table>
 */
public class Regex {
  private Pattern pattern;
  private String rawRegex;
  private final Object privatesyncobj = new Object();
  Map<CapturingGroup, Integer> groupToIndex = new HashMap<>();
  int numGroups = 0;

  /*
   ****************
   * Constructors *
   ****************
   */

  /**
   * Constructs a Regular Expression from the regular expression subcomponents.
   * Note that this method does not escape any characters
   * @param components the sub-components of the regular expression
   */
  Regex(String... components) {
    StringBuilder b = new StringBuilder();
    for(String inner : components) {
      b.append(inner);
    }
    rawRegex = b.toString();
    pattern = null;
  }


  /**
   * Constructs a Regular Expression from the regular expression subcomponents.
   * Takes group indexing regex from a base regex.
   * Note that this method does not escape any characters
   * @param base the regex to copy group indexing info from
   * @param components the sub-components of the regular expression
   */
  Regex(Regex base, String... components) {
    this(components);
    this.numGroups = base.numGroups;
    this.groupToIndex = base.groupToIndex;
  }

  /**
   * Appends a regex's string to a builder, while also updating capturing group information.
   * @param b
   * @param regex
   */
  private void appendRegex(StringBuilder b, Regex regex) {
    b.append(regex.rawRegex);
    final int curGroupIndex = numGroups;
    regex.groupToIndex.forEach((group, index) -> {
      if (this.groupToIndex.containsKey(group)) {
        // Remove label from capturing group
        String toFind = "?<" + group.label + ">";
        int startIndex = b.indexOf(toFind);
        assert(startIndex != -1);
        b.delete(startIndex, startIndex + toFind.length());
      }
      this.groupToIndex.put(group, index + curGroupIndex);
    });
    numGroups += regex.numGroups;
  }

  /**
   * Constructs a regular expression from the given sub-components. The
   * new Regex constructed will be the concatenation of all of the subcomponents.
   *
   * In other words, if the subcomponents match "a", "b" and "c", then the new regular
   * expression will match "abc"
   * @param components the sub-components of the regular expression
   */
  public Regex(Regex... components) {
    StringBuilder b = new StringBuilder();
    numGroups = 0;
    for(Regex inner : components) {
      appendRegex(b, inner);
    }
    rawRegex = b.toString();
    pattern = null;
  }


  /*
   **************
   * Characters *
   **************
   */

  /**
   * Returns a regex that matches a single character
   * @param c the character class
   * @return a regex that matches a single character
   */
  public static Regex single(char c) {
    return new Regex(sanitized(c));
  }

  /**
   * Returns a regex that matches a given string exactly. The given string has any special regex characters
   * escaped automatically.
   * @param s the string to match against
   * @return a regex that matches the given string
   */
  public static Regex string(String s) {
    return new Regex(sanitized(s));
  }

  /**
   * Constructs a regular expression from the given raw regular expression. This Regular expression
   * should conform to the Java Regex language. See {@link Pattern}
   *
   * @param regex the sub-components of the regular expression
   * @return a new regular expression matching the given regex
   */
  public static Regex fromRawRegex(String regex) {
    return new Regex(regex);
  }
  /*
   *********************
   * Character Classes *
   *********************
   */

  /**
   * Returns a regex that matches a single character within a given character class
   * @param c the character class
   * @return a regex that matches a single character within a given character class
   */
  public static Regex single(CharacterClass c) {
    return c;
  }


  /*
   **************
   * Boundaries *
   **************
   */

  /**
   * The start of a line. If multi-line mode is off, then this matches the start of input
   */
  public static final Regex LINE_START = new Regex("^");

  /**
   * The end of a line. If multi-line mode is off, then this matches the end of input
   */
  public static final Regex LINE_END = new Regex("$");

  /*
   ***************
   * Quantifiers *
   ***************
   */

  /**
   * Returns a regex which matches any string that consists of the given string either once or not at all
   * @param s the string to compose on
   * @return a regex which matches any string that consists of the given string repeated at most once
   */
  public static Regex optional(String s) {
    if(s.length() == 1) {
      return new Regex(sanitized(s.charAt(0)), "?");
    } else {
      return new Regex("(?:", sanitized(s), ")?");
    }
  }

  /**
   * Returns a regex which matches any string that consists of any string the given regex matches repeated either once or not at all
   * @param r the regex to compose on
   * @return a regex which matches any string that consists of any string the given regex matches repeated either once or not at all
   */
  public static Regex optional(Regex r) {
    return new Regex(r, r.selfAsGrouped(), "?");
  }

  /**
   * Returns a regex which matches any string that consists of the given string repeated any number of times.
   * @param s the string to compose on
   * @return a regex which matches any string that consists of the given string repeated any number of times.
   */
  public static Regex anyAmount(String s) {
    if(s.length() == 1) {
      return new Regex(sanitized(s.charAt(0)), "*");
    } else {
      return new Regex("(?:", sanitized(s), ")*");
    }
  }

  /**
   * Returns a regex which matches any string that consists of the given regex repeated any number of times.
   * @param r the regex to compose on
   * @return a regex which matches any string that consists of the given regex repeated any number of times.
   */
  public static Regex anyAmount(Regex r) {
    return new Regex(r, r.selfAsGrouped(), "*");
  }

  /**
   * Returns a regex which matches any string that consists of the given character repeated at least once.
   * @param c the character to compose on
   * @return a regex which matches any string that consists of the given character repeated at least once.
   */
  public static Regex atLeastOne(char c) {
    return new Regex(sanitized(c), "+");
  }

  /**
   * Returns a regex which matches any string that consists of any string the given regex matches repeated at least once.
   * @param r the regex to compose on
   * @return a regex which matches any string that consists of any string the given regex matches repeated at least once.
   */
  public static Regex atLeastOne(Regex r) {
    return new Regex(r, r.selfAsGrouped(), "+");
  }

  /**
   * Returns a regex which matches any string that consists of the given string repeated between min and max times
   * @param s the string to repeat
   * @param min the minimum number of times the character should repeat (inclusive)
   * @param max the maximum number of times the character should repeat (inclusive)
   * @return a regex which matches any string that consists of the given string repeated between min and max times
   */
  public static Regex repeat(String s, int min, int max) {
    if(s.length() == 1) {
      return new Regex(sanitized(s.charAt(0)),
              "{", Integer.toString(min), ",",
              Integer.toString(max), "}");
    } else {
      return new Regex(
              "(?:", sanitized(s), ")",
              "{", Integer.toString(min), ",",
              Integer.toString(max), "}");
    }
  }

  /**
   * Returns a regex which matches any string that consists of any string the given regex matches repeated exactly amount times
   * @param g the regex to repeat
   * @param amount the number of times the regex should repeat
   * @return a regex which matches any string that consists of any string the given regex matches repeated exactly amount times
   */
  public static Regex repeatExactly(Regex g, int amount) {
    return new Regex(g, g.selfAsGrouped(),
            "{", Integer.toString(amount), "}");
  }

  /**
   * Returns a regex which matches any string that consists of the given string repeated at least min times
   * @param s the string to repeat
   * @param min the minimum number of times the character should repeat (inclusive)
   * @return a regex which matches any string that consists of the given string repeated at least min times
   */
  public static Regex repeatAtLeast(String s, int min) {
    if(s.length() == 1) {
      return new Regex(sanitized(s.charAt(0)),
              "{", Integer.toString(min), ",}");
    } else {
      return new Regex(
              "(?:", sanitized(s), ")",
              "{", Integer.toString(min), ",}");
    }
  }

  /**
   * Returns a regex which matches any string that consists of any string the given regex matches repeated at least min times
   * @param g the regex to repeat
   * @param min the minimum number of times the regex should repeat (inclusive)
   * @return a regex which matches any string that consists of any string the given regex matches repeated at least min times
   */
  public static Regex repeatAtLeast(Regex g, int min) {
    return new Regex(g, g.selfAsGrouped(),
            "{", Integer.toString(min), ",}");
  }

  /**
   * Returns a regex which matches any string that consists of the given string repeated at most max times
   * @param s the string to repeat
   * @param max the maximum number of times the character should repeat (inclusive)
   * @return a regex which matches any string that consists of the given string repeated at most max times
   */
  public static Regex repeatAtMost(String s, int max) {
    if(s.length() == 1) {
      return new Regex(sanitized(s.charAt(0)),
              "{0,", Integer.toString(max), "}");
    } else {
      return new Regex(
              "(?:", sanitized(s), ")",
              "{0,", Integer.toString(max), "}");
    }
  }

  /**
   * Returns a regex which matches any string that consists of any string the given regex matches repeated at most max times
   * @param g the regex to repeat
   * @param max the maximum number of times the regex should repeat (inclusive)
   * @return a regex which matches any string that consists of any string the given regex matches repeated at most max times
   */
  public static Regex repeatAtMost(Regex g, int max) {
    return new Regex(g, g.selfAsGrouped(),
            "{0,", Integer.toString(max), "}");
  }

  /**
   * Returns a regex which matches any string that consists of the given string repeated exactly amount times
   * @param s the string to repeat
   * @param amount the number of times the regex should repeat
   * @return a regex which matches any string that consists of the given string repeated exactly amount times
   */
  public static Regex repeatExactly(String s, int amount) {
    if(s.length() == 1) {
      return new Regex(sanitized(s.charAt(0)),
              "{", Integer.toString(amount), "}");
    } else {
      return new Regex(
              "(?:", sanitized(s), ")",
              "{", Integer.toString(amount), "}");
    }
  }

  /**
   * Returns a regex which matches any string that consists of any string the given regex matches repeated between min and max times
   * @param g the regex to repeat
   * @param min the minimum number of times the regex should repeat (inclusive)
   * @param max the maximum number of times the regex should repeat (inclusive)
   * @return a regex which matches any string that consists of any string the given regex matches repeated between min and max times
   */
  public static Regex repeat(Regex g, int min, int max) {
    return new Regex(g, g.selfAsGrouped(),
            "{", Integer.toString(min), ",",
            Integer.toString(max), "}");
  }

  /*
   *********************
   * Logical Operators *
   *********************
   */

  /**
   * Constructs a regular expression from the given sub-components. The
   * new Regex constructed will be the concatenation of all of the subcomponents.
   *
   * In other words, if the subcomponents match "a", "b" and "c", then the new regular
   * expression will match "abc"
   * @param components the sub-components of the regular expression
   * @return a new regular expression matching the concatenation of the arguments
   */
  public static Regex concatenate(Regex... components) {
    return new Regex(components);
  }

  /**
   * Returns a regex which matches one of the given regular expressions. If no regular expressions
   * are provided in the arguments, a regular expression matching nothing is returned
   * @param rs the regular expressions that are the options
   * @return a regex which matches one of the given regular expressions
   */
  public static Regex oneOf(Regex... rs) {
    if(rs.length > 1) {
      Regex regex = new Regex("");
      StringBuilder b = new StringBuilder();
      b.append("(?:");
      regex.appendRegex(b, rs[0]);
      for (int i = 1; i < rs.length; i++) {
        b.append("|");
        regex.appendRegex(b, rs[i]);
      }
      b.append(")");
      regex.rawRegex = b.toString();
      return regex;
    } else if (rs.length == 1) {
      return rs[0];
    } else {
      return new Regex("");
    }
  }

  /*
   *******************
   * Back-References *
   *******************
   */

  /**
   * Returns a new regex that matches the same thing that the i^th capturing group within a larger regex matched.
   *
   * See Capturing Groups in the class-level documentation
   * @param i Which capturing group to match on
   * @return a regex that matches the same thing that the i^th capturing group within a larger regex matched.
   */
  public static Regex backReference(int i) {
    return new Regex("\\", Integer.toString(i));
  }

  /**
   * Returns a new regex that matches the last thing that the capturing group passed in matched.
   *
   * @param group Which capturing group to match on
   * @return a regex that matches the same thing that the last instance of group matched.
   */
  public static Regex backReference(CapturingGroup group) {
    return new Regex("\\k", group.label);
  }

  /*
   **************
   * Lookaround *
   **************
   */

  /**
   * Returns a new regex that matches any string that the given regex matches, without consuming any characters.
   * The returned regex essentially acts as an "assert" that the rest of the string matches.
   *
   * In technical terms, the new regex performs zero-width positive lookahead on the provided regex
   *
   * @param r the regex to perform lookahead on
   * @return a new regex that asserts that the rest of the string matches r, but does not consume any characters
   */
  public static Regex lookahead(Regex r) {
    return new Regex(r,
            "(?=",
            r.rawRegex,
            ")"
    );
  }

  /**
   * Returns a new regex that matches any string that the given regex does not match, without consuming any characters.
   * The returned regex essentially acts as an "assert" that the rest of the string does not match.
   *
   * In technical terms, the new regex performs zero-width negative lookahead on the provided regex
   *
   * @param r the regex to perform negative lookahead on
   * @return a new regex that asserts that the rest of the string does not match r, but does not consume any characters
   */
  public static Regex negativeLookahead(Regex r) {
    return new Regex(r,
            "(?!",
            r.rawRegex,
            ")"
    );
  }

  /**
   * Returns a new regex that asserts that the preceding part of the string (within a larger regex) matches the provided
   * regex <code>r</code>, without consuming any characters.
   * The returned regex essentially acts as an "assert" that the previous part of the string matches.
   *
   * In technical terms, the new regex performs zero-width positive lookbehind on the provided regex
   *
   * @param r the regex to perform lookbehind on
   * @return a new regex that asserts that the preceding part of the string matches r, but does not consume any characters
   */
  public static Regex lookbehind(Regex r) {
    return new Regex(r,
            "(?<=",
            r.rawRegex,
            ")"
    );
  }

  /**
   * Returns a new regex that asserts that the preceding part of the string (within a larger regex) does not match the
   * provided regex <code>r</code>, without consuming any characters.
   * The returned regex essentially acts as an "assert" that the previous part of the string does not match.
   *
   * In technical terms, the new regex performs zero-width negative lookbehind on the provided regex
   *
   * @param r the regex to perform negative lookbehind on
   * @return a new regex that asserts that the preceding part of the string does not match r, but does not consume any characters
   */
  public static Regex negativeLookbehind(Regex r) {
    return new Regex(r,
            "(?<!",
            r.rawRegex,
            ")"
    );
  }

  /*
   ************
   * Matching *
   ************
   */

  /**
   * Returns a stream of all matches to this regex.
   * @param input The string that the regex should be matched against
   * @return a stream of all matches to this regex.
   */
  public Stream<RegexMatch> getMatches(String input) {
    Matcher m = getMatcher(input);
    return m.results().map((result) -> new RegexMatch(result, this));
  }

  /**
   * Get the i'th section of the input that matches this regex.
   *
   * As an example, if the regex r matches "a", then r.getMatch("abca", 1) will
   * return a match on the last character of "abca"
   *
   * @param input The string that the regex should be matched against
   * @param i which match to return
   * @return the i'th section of the input that matches this regex
   */
  public RegexMatch getMatch(String input, int i) {
    Matcher m = getMatcher(input);
    for (int j = 0; j <= i; j++) {
      m.find();
    }
    return new RegexMatch(m.toMatchResult(), this);
  }

  /**
   * Get the first section of the input that matches this regex.
   *
   * This method is equivalent to getMatch(input, 0)
   *
   * @param input The string that the regex should be matched against
   * @return the first section of the input that matches this regex
   */
  public RegexMatch firstMatch(String input) {
    Matcher m = getMatcher(input);
    m.find();
    return new RegexMatch(m.toMatchResult(), this);
  }

  /**
   * Checks whether the input matches this regex.
   *
   * @param input The string that the regex should be matched against
   * @return whether the input matches this regex
   */
  public boolean doesMatch(String input) {
    Matcher m = getMatcher(input);
    return m.find();
  }

  public String replace(String input, ReplacementRegex replacement) {
    Matcher m = getMatcher(input);
    return m.replaceAll(replacement.toString());
  }

  public String replace(String input, ReplacementLambda l) {
    Matcher m = getMatcher(input);
    return m.replaceAll(match -> l.matchCallback(new RegexMatch(match, this)));
  }
  
  /*
   *******************
   * Public Helpers *
   *******************
   */

  /**
   * Returns the raw regex string that this regex is composed of
   * @return the raw regex string that this regex is composed of
   */
  public String getRawRegex() {
    return rawRegex;
  }

  @Override
  public String toString() {
    return rawRegex;
  }

  /*
   *******************
   * Private Helpers *
   *******************
   */

  /**
   * Returns a matcher on the given input
   * @param input the input to match on
   * @return A matcher for the given input
   */
  private Matcher getMatcher(String input) {
    if (pattern == null) {
      synchronized (privatesyncobj) {
        if(pattern == null) {
          pattern = pattern.compile(rawRegex);
        }
      }
    }
    return pattern.matcher(input);
  }

  private static final List<Character> metacharacters = Arrays.asList('?', '\\', '-', '=', '[', ']', '(', ')',
                                                                      '{', '}', '<', '>', '!', '*', '.', '+',
                                                                      '^', '$', '|' );
  /**
   * Returns the given character as a string, escaped if necessary
   * @param c The character to sanitize
   * @return the given character as a string, escaped if necessary
   */
  static String sanitized(char c) {
    StringBuilder b = new StringBuilder();
    if(metacharacters.contains(c)) {
      b.append('\\');
    }
    b.append(c);

    return b.toString();
  }

  /**
   * Returns the given string, escaping any metacharacters that appear
   * @param s the string to sanitize
   * @return the given string, escaped if necessary
   */
  static String sanitized(String s) {
    //TODO(astanesc): Use a regex or .contains?
    StringBuilder b = new StringBuilder();
    for (char c : s.toCharArray()) {
      b.append(sanitized(c));
    }
    return b.toString();
  }

  /**
   * Returns this regex as a group that can be used with quantifiers
   *
   * The default implementation returns the regex within a non-capturing group
   * @return this regex as a group
   */
  String selfAsGrouped() {
    StringBuilder r = new StringBuilder();
    r.append("(?:");
    r.append(rawRegex);
    r.append(")");
    return r.toString();
  }

}
