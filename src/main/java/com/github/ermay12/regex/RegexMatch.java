package com.github.ermay12.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * A RegexMatch represents a single match on a String by a regex. It allows you to get
 * the string of the match, as well as the index in the entire string. It also allows youo
 * to extract the individual capturing groups that are in the regex, either by passing the group
 * in or using the index of the group (see: {@link CapturingGroup} for information on group indexes).
 *
 * Each RegexMatch also has a match index associated with it, which is telling which match in the
 * original string this is.
 */
public final class RegexMatch {
  private final String matchString;
  private final RegexLiteral regex;
  private final int matchIndex;
  private final int matchStartIndex;
  private final int matchStopIndex;
  private final List<String> groups;
  private final List<Integer> groupStartIndexes;
  private final List<Integer> groupStopIndexes;

  /**
   * Creates an immutable regex match object.
   * @param m a matcher object that has already matched this regex match
   * @param regex the regex used to find this match
   * @param matchIndex the index relative to the original string
   */
  RegexMatch(Matcher m, RegexLiteral regex, int matchIndex) {
    this.matchString = m.group();
    int numGroups = m.groupCount() + 1;
    this.groups = new ArrayList<>();
    this.groupStartIndexes = new ArrayList<>();
    this.groupStopIndexes = new ArrayList<>();
    for (int i = 0; i < numGroups; i++) {
      groups.add(m.group(i));
      groupStartIndexes.add(m.start(i));
      groupStopIndexes.add(m.end(i));
    }
    this.regex = regex;
    this.matchIndex = matchIndex;
    this.matchStartIndex = m.start();
    this.matchStopIndex = m.end();
  }

  /**
   * Gets the matched string
   * @return the matched string
   */
  public String matchString() {
    return matchString;
  }
  /**
   * gets the string of a given group
   * @param i the group index.  0 always returns the match string. 1 returns the first group.
   * @return the group string
   */
  public String group(int i) {
    return this.groups.get(i);
  }

  /**
   * gets the string of a given group
   * @param group the group within the match
   * @return the group string
   */
  public String group(CapturingGroup group) {
    if (!this.regex.groupToIndex.containsKey(group)) {
      throw new IllegalArgumentException("Group passed in is not present in regex!");
    }
    return groups.get(this.regex.groupToIndex.get(group));
  }


  /**
   * Gets the index of this match relative to all matches in the original string.
   * @return the index of this match
   */
  public int matchIndex() {
    return matchIndex;
  }

  /**
   * gets the start index of this match relative to the original string
   * @return the start index of this match relative to the original string
   */
  public int matchStartIndex(){
    return this.matchStartIndex;
  }

  /**
   * Gets the index of the character after the last character in this match in the original string
   * @return the index of the character after the last character in this match in the original string
   */
  public int matchStopIndex(){
    return this.matchStopIndex;
  }

  /**
   * Gets the index of the start of a given group relative to the original string
   * @param i the group index
   * @returnindex of the start of a given group relative to the original string
   */
  public int groupStartIndex(int i){
    return groupStartIndexes.get(i);
  }

  /**
   * Gets the index of the start of a given group relative to the original string
   * @param group the group
   * @returnindex of the start of a given group relative to the original string
   */
  public int groupStartIndex(CapturingGroup group){
    return groupStartIndexes.get(this.regex.groupToIndex.get(group));
  }

  /**
   * Gets the index of the end of a given group relative to the original string
   * @param i the group index.  0 represents the whole match.  1 represents the first group
   * @returnindex of the end of a given group relative to the original string
   */
  public int groupStopIndex(int i){
    return groupStopIndexes.get(i);
  }

  /**
   * Gets the index of the end of a given group relative to the original string
   * @param group the group
   * @returnindex of the end of a given group relative to the original string
   */
  public int groupStopIndex(CapturingGroup group){
    return groupStopIndexes.get(this.regex.groupToIndex.get(group));
  }


  /**
   * Gets the matched string
   * @return the matched string
   */
  @Override
  public String toString() {
    return matchString;
  }
}
