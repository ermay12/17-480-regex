package com.github.ermay12.regex;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;

public class RegexMatch {
  String matchString;
  MatchResult match;
  Regex regex;

  RegexMatch(MatchResult m, Regex regex) {
    this.matchString = m.group();
    this.match = m;
    this.regex = regex;
  }

  public String getMatchString() {
    return matchString;
  }

  public String getGroup(CapturingGroup group) {
    if (!this.regex.groupToIndex.containsKey(group)) {
      throw new IllegalArgumentException("Group passed in is not present in regex!");
    }
    return this.match.group(this.regex.groupToIndex.get(group));
  }

  public String getGroup(int number) {
    return this.match.group(number);
  }

  @Override
  public String toString() {
    return matchString;
  }
}
