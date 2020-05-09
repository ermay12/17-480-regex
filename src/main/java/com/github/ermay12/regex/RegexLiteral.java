package com.github.ermay12.regex;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class RegexLiteral {
  Pattern pattern;
  String rawRegex;
  final Object privatesyncobj = new Object();
  Map<CapturingGroup, Integer> groupToIndex = new HashMap<>();
  int numGroups = 0;

  /*
   ****************
   * Constructors *
   ****************
   */

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
  RegexLiteral(CharSequence... components) {
    StringBuilder b = new StringBuilder();
    for (CharSequence inner : components) {
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
  RegexLiteral(RegexLiteral base, CharSequence... components) {
    this(components);
    this.numGroups = base.numGroups;
    this.groupToIndex.putAll(base.groupToIndex);
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
  RegexLiteral(RegexLiteral... components) {
    StringBuilder b = new StringBuilder();
    numGroups = 0;
    for (RegexLiteral inner : components) {
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
   * @param input The CharSequence that the regex should be matched against
   * @return a stream of all matches to this regex.
   */
  public Stream<RegexMatch> getMatches(CharSequence input) {
    Matcher m = getMatcher(input);
    if (m.find()) {
      RegexMatch firstMatch = new RegexMatch(m, this, 0);
      return Stream.iterate(firstMatch, (prevMatch) -> {
        if (m.find()) {
          return new RegexMatch(m, this, prevMatch.getIndex() + 1);
        } else {
          return null;
        }
      }).takeWhile(match-> match != null);
    } else {
      return Stream.empty();
    }
  }

  /**
   * Get the i'th section of the input that matches this regex.
   * <p>
   * As an example, if the regex r matches "a", then r.getMatch("abca", 1) will
   * return a match on the last character of "abca"
   *
   * @param input The CharSequence that the regex should be matched against
   * @param i     which match to return. 0-indexed
   * @return the i'th section of the input that matches this regex,
   * or empty if there are less than i + 1 matches
   */
  public Optional<RegexMatch> getMatch(CharSequence input, int i) {
    Matcher m = getMatcher(input);
    for (int j = 0; j <= i; j++) {
      if (!m.find()) {
        return Optional.empty();
      }
    }
    return Optional.of(new RegexMatch(m, this, i));
  }

  /**
   * Get the first section of the input that matches this regex.
   * <p>
   * This method is equivalent to getMatch(input, 0)
   *
   * @param input The CharSequence that the regex should be matched against
   * @return the first section of the input that matches this regex, or empty if there is no match
   */
  public Optional<RegexMatch> firstMatch(CharSequence input) {
    Matcher m = getMatcher(input);
    if (m.find()) {
      return Optional.of(new RegexMatch(m, this, 0));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Checks whether the input matches this regex.
   *
   * @param input The CharSequence that the regex should be matched against
   * @return whether the input matches this regex
   */
  public boolean doesMatch(CharSequence input) {
    Matcher m = getMatcher(input);
    return m.find();
  }

  public String replace(CharSequence input, ReplacementRegex replacement) {
    Matcher m = getMatcher(input);
    return m.replaceAll(replacement.toString());
  }

  public String replace(CharSequence input, Function<RegexMatch, String> l) {
    Matcher m = getMatcher(input);
    StringBuilder sb = new StringBuilder();
    int i = 0;
    while (m.find()) {
      String s = l.apply(new RegexMatch(m, this, i));
      m.appendReplacement(sb, s);
      i++;
    }
    m.appendTail(sb);
    return sb.toString();
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
  void appendRegex(StringBuilder b, RegexLiteral regex) {
    ArrayList<Integer> removedValues = new ArrayList<>();
    final int curGroupIndex = numGroups;
    regex.groupToIndex.forEach((group, index) -> {
      if (this.groupToIndex.containsKey(group)) {
        // Remove label from capturing group
        String toFind = "?<" + group.label + ">";
        int startIndex = b.indexOf(toFind);
        assert (startIndex != -1);

        b.delete(startIndex, startIndex + toFind.length());

        String badBackReference = "\\k<" + group.label + ">";
        if (b.indexOf(badBackReference) != -1) {
          throw new IllegalArgumentException("Backreference made to group " + group.toString() +
              " that becomes illegal due to concatenation!");
        }
      }
      this.groupToIndex.put(group, index + curGroupIndex);
    });

    numGroups += regex.numGroups;
    b.append(regex.rawRegex);
  }

  /**
   * Returns a matcher on the given input
   *
   * @param input the input to match on
   * @return A matcher for the given input
   */
  private Matcher getMatcher(CharSequence input) {
    if (pattern == null) {
      synchronized (privatesyncobj) {
        if (pattern == null) {
          pattern = pattern.compile(rawRegex);
        }
      }
    }
    return pattern.matcher(input);
  }

  private static final List<Character> metacharacters = Arrays.asList('?', '\\', '-', '=', '[', ']', '(', ')',
      '{', '}', '<', '>', '!', '*', '.', '+',
      '^', '$', '|');

  /**
   * Returns the given character as a string, escaped if necessary
   *
   * @param c The character to sanitize
   * @return the given character as a string, escaped if necessary
   */
  static String sanitized(char c) {
    StringBuilder b = new StringBuilder();
    if (metacharacters.contains(c)) {
      b.append('\\');
    }
    b.append(c);

    return b.toString();
  }

  /**
   * Returns the given string, escaping any metacharacters that appear
   *
   * @param s the string to sanitize
   * @return the given string, escaped if necessary
   */
  static String sanitized(CharSequence s) {
    //TODO(astanesc): Use a regex or .contains?
    StringBuilder b = new StringBuilder();
    int stringLength = s.length();
    for (int i = 0; i < stringLength; i++) {
      char c = s.charAt(i);
      b.append(sanitized(c));
    }
    return b.toString();
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
