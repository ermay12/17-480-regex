package com.github.ermay12.regex;

/**
 * This class represents a regex replacement.  It currently only works with raw replacement strings.
 */
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
