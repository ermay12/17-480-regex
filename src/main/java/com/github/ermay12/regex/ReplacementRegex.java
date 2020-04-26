package com.github.ermay12.regex;

public class ReplacementRegex {
  String replacementString;

  public ReplacementRegex(String replacementString) {
    this.replacementString = replacementString;
  }

  @Override
  public String toString() {
    return replacementString;
  }
}
