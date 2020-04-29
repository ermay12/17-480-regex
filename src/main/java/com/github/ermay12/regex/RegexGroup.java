package com.github.ermay12.regex;

public class RegexGroup extends Regex{
  public RegexGroup(String... components) {
    super(components);
    this.rawRegex = "(" + this.rawRegex+ ")";
  }
  public RegexGroup(Regex... components){
    super(components);
    this.rawRegex = "(" + this.rawRegex+ ")";
  }
}
