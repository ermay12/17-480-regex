package com.github.ermay12.regex;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;

public class RegexMatch {
  String matchString;
  MatchResult match;

  public RegexMatch(MatchResult m) {
    this.matchString = m.group(0);
    this.match = m;
  }

  public String getGroup(int i) {
    return this.match.group(i);
  }

  @Override
  public String toString() {
    return matchString;
  }
}
