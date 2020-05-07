package com.github.ermay12.regex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
