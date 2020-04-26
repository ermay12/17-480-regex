/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.ermay12.regex;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Regex {
  private Pattern pattern;

  public Regex(String expression) {
    pattern = Pattern.compile(expression);
  }

  public RegexMatch getMatch(String input, int i) {
    Matcher m = pattern.matcher(input);
    for (int j = 0; j <= i; j++) {
      m.find();
    }
    return new RegexMatch(m);
  }

  public RegexMatch firstMatch(String input) {
    Matcher m = pattern.matcher(input);
    m.find();
    return new RegexMatch(m);
  }

  public boolean doesMatch(String input) {
    Matcher m = pattern.matcher(input);
    return m.find();
  }

  public String replace(String input, ReplacementRegex replacement) {
    Matcher m = pattern.matcher(input);
    return m.replaceAll(replacement.toString());
  }

  @FunctionalInterface
  public interface ReplacementLambda {
    String matchCallback(RegexMatch match);
  }

  public String replace(String input, ReplacementLambda lambda) {
    Matcher m = pattern.matcher(input);
    return m.replaceAll((MatchResult match) -> {
      return lambda.matchCallback(new RegexMatch(match));
    });
  }

  public Stream<RegexMatch> getMatches(String input) {
    Matcher m = pattern.matcher(input);
    return m.results().map((MatchResult match) -> {
      return new RegexMatch(match);
    });
  }
}