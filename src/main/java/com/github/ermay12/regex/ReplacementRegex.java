package com.github.ermay12.regex;

public class ReplacementRegex {
  String replacementString;

  public ReplacementRegex(CharSequence replacementString) {
    this.replacementString = replacementString.toString();
  }

  @Override
  public String toString() {
    return replacementString;
  }
}
