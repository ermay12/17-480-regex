package com.github.ermay12.regex;

import java.util.HashMap;
import java.util.Map;
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
 *  <tr style="text-align: left"><th colspan="2" id="quant">Alternative Evaluation type quantifiers</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>optional(X, T)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, either once or not at all, using the provided {@link EvaluationMethod} T</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>anyAmount(X, T)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, zero or more times, using the provided {@link EvaluationMethod} T</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>atLeastOne(X, T)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, one or more times, using the provided {@link EvaluationMethod} T</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeatExactly(X, amount, T)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, repeated exactly amount times, using the provided {@link EvaluationMethod} T</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeatAtLeast(X, min, T)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, min or more times, using the provided {@link EvaluationMethod} T</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeatAtMost(X, max, T)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, repeated at most max times, using the provided {@link EvaluationMethod} T</td>
 *  </tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct quant"><i>repeat(X, min, max, T)</i></td>
 *    <td style="padding: 1px; padding-left: 5px;" headers="matches">X, repeated at least min and at most max times, using the provided {@link EvaluationMethod} T</td>
 *  </tr>
 *
 *  <tr><th>&nbsp;</th></tr>g
 *  <tr style="text-align: left"><th colspan="2" id="logical">Logical Operators</th></tr>
 *  <tr>
 *    <td style="padding: 1px;" valign="top" headers="construct logical"><i>new RegularExpression(X1, X2, ...)</i></td>
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
public class RegularExpression {
  private Pattern pattern;
  String rawRegex;
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
   *
   * @param components the sub-components of the regular expression
   */
  RegularExpression(String... components) {
    StringBuilder b = new StringBuilder();
    for (String inner : components) {
      b.append(inner);
    }
    rawRegex = b.toString();
    pattern = null;
  }


  /**
   * Constructs a Regular Expression from the regular expression subcomponents.
   * Takes group indexing regex from a base regex.
   * Note that this method does not escape any characters
   *
   * @param base       the regex to copy group indexing info from
   * @param components the sub-components of the regular expression
   */
  RegularExpression(RegularExpression base, String... components) {
    this(components);
    this.numGroups = base.numGroups;
    this.groupToIndex = base.groupToIndex;
  }

  /**
   * Constructs a regular expression from the given sub-components. The
   * new Regex constructed will be the concatenation of all of the subcomponents.
   * <p>
   * In other words, if the subcomponents match "a", "b" and "c", then the new regular
   * expression will match "abc"
   *
   * @param components the sub-components of the regular expression
   */
  public RegularExpression(RegularExpression... components) {
    StringBuilder b = new StringBuilder();
    numGroups = 0;
    for (RegularExpression inner : components) {
      appendRegex(b, inner);
    }
    rawRegex = b.toString();
    pattern = null;
  }


  /*
   ************
   * Matching *
   ************
   */

  /**
   * Returns a stream of all matches to this regex.
   *
   * @param input The string that the regex should be matched against
   * @return a stream of all matches to this regex.
   */
  public Stream<RegexMatch> getMatches(String input) {
    Matcher m = getMatcher(input);
    return m.results().map((result) -> new RegexMatch(result, this));
  }

  /**
   * Get the i'th section of the input that matches this regex.
   * <p>
   * As an example, if the regex r matches "a", then r.getMatch("abca", 1) will
   * return a match on the last character of "abca"
   *
   * @param input The string that the regex should be matched against
   * @param i     which match to return
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
   * <p>
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
   *
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
   * Appends a regex's string to a builder, while also updating capturing group information.
   *
   * @param b     the builder being used
   * @param regex the regex, which may contain capturing groups
   */
  void appendRegex(StringBuilder b, RegularExpression regex) {
    b.append(regex.rawRegex);
    final int curGroupIndex = numGroups;
    regex.groupToIndex.forEach((group, index) -> {
      if (this.groupToIndex.containsKey(group)) {
        // Remove label from capturing group
        String toFind = "?<" + group.label + ">";
        int startIndex = b.indexOf(toFind);
        assert (startIndex != -1);
        b.delete(startIndex, startIndex + toFind.length());
      }
      this.groupToIndex.put(group, index + curGroupIndex);
    });
    numGroups += regex.numGroups;
  }

  /**
   * Returns a matcher on the given input
   *
   * @param input the input to match on
   * @return A matcher for the given input
   */
  private Matcher getMatcher(String input) {
    if (pattern == null) {
      synchronized (privatesyncobj) {
        if (pattern == null) {
          pattern = pattern.compile(rawRegex);
        }
      }
    }
    return pattern.matcher(input);
  }


  /**
   * Returns this regex as a group that can be used with quantifiers
   * <p>
   * The default implementation returns the regex within a non-capturing group
   *
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
