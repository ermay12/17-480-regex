package com.github.ermay12.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public final class RegexMatch {
  private final String matchString;
  private final RegexLiteral regex;
  private final int matchIndex;
  private final int matchStartIndex;
  private final int matchStopIndex;
  private final List<String> groups;
  private final List<Integer> groupStartIndexes;
  private final List<Integer> groupStopIndexes;

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


  public String getMatchString() {
    return matchString;
  }

  public String getGroup(CapturingGroup group) {
    if (!this.regex.groupToIndex.containsKey(group)) {
      throw new IllegalArgumentException("Group passed in is not present in regex!");
    }
    return groups.get(this.regex.groupToIndex.get(group));
  }

  public int matchIndex() {
    return matchIndex;
  }

  public int matchStartIndex(){
    return this.matchStartIndex;
  }

  public int getMatchStopIndex(){
    return this.matchStopIndex;
  }

  public int getGroupStartIndex(int i){
    return groupStartIndexes.get(i);
  }

  public int getGroupStartIndex(CapturingGroup group){
    return groupStartIndexes.get(this.regex.groupToIndex.get(group));
  }


  public int getGroupStopIndex(int i){
    return groupStopIndexes.get(i);
  }

  public int getGroupStopIndex(CapturingGroup group){
    return groupStopIndexes.get(this.regex.groupToIndex.get(group));
  }


  public String getGroup(int number) {
    return this.groups.get(number);
  }

  @Override
  public String toString() {
    return matchString;
  }
}
