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
public class RegexMatch {
  String matchString;
  RegexLiteral regex;
  int index;
  List<String> groups;

  RegexMatch(Matcher m, RegexLiteral regex, int index) {
    this.matchString = m.group();
    int numGroups = m.groupCount() + 1;
    this.groups = new ArrayList<>();
    for (int i = 0; i < numGroups; i++) {
      groups.add(m.group(i));
    }
    this.regex = regex;
    this.index = index;
  }


  public String getMatchString() {
    return matchString;
  }

  public String getGroup(CapturingGroup group) {
    if (!this.regex.groupToIndex.containsKey(group)) {
      throw new IllegalArgumentException("Group passed in is not present in regex!");
    }
    return groups.get(this.regex.groupToIndex.get(group));
  }

  public int getIndex() {
    return index;
  }

  public String getGroup(int number) {
    return this.groups.get(number);
  }

  @Override
  public String toString() {
    return matchString;
  }
}
