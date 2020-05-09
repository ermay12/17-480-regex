package com.github.ermay12.regex;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p> A CapturingGroup represents a subsection of a larger Regex which you can extract matches from.
 * For instance, if you wanted a regex to match an email such as "test@example.com", you could use
 * two CapturingGroups to "capture" both the name (test) and the domain (example.com) and separate that
 * out. An example of this is: </p>
 * <pre>
 *     CapturingGroup nameGroup = CapturingGroup.capture(anyAmount(not('@'));
 *     CapturingGroup domainGroup = CapturingGroup.capture(anyAmount(not('.')), string("."), anyAmount(WILDCARD));
 *     Regex emailRegex = concatenate(nameGroup, string("@"), domainGroup);
 *
 *     String test = "test@example.com";
 *     MatchResult result = emailRegex.firstMatch(test);
 *     result.getGroup(nameGroup); // "test"
 *     result.getGroup(emailGroup); // "example.com"
 * </pre>
 * <p> If the same CapturingGroup is present in a regex multiple times, the last match will be used as the value.
 * This also applies, for instance, if the CapturingGroup is in an "anyAmount", as the last match from that
 * will be the value. </p>
 *
 * <p> Capturing groups are numbered by counting their opening parentheses from
 * left to right.  In the expression </p>
 * <pre>
 *   capturing(
 *     capturing(string("A")),
 *     capturing(
 *       string("B"),
 *       capturing(string("C")
 *     )
 *   )
 * </pre>
 * <p> there are four such groups: </p>
 *
 * <blockquote><table><caption>Capturing group numberings</caption>
 * <tbody><tr><th>1&nbsp;&nbsp;&nbsp;&nbsp;</th>
 * <td>The whole group</td></tr>
 * <tr><th>2&nbsp;&nbsp;&nbsp;&nbsp;</th>
 * <td>capturing(string("A"))</td></tr>
 * <tr><th>3&nbsp;&nbsp;&nbsp;&nbsp;</th>
 * <td>capturing(concatenate(string("B"), capturing(string("C"))))
 * <tr><th>4&nbsp;&nbsp;&nbsp;&nbsp;</th>
 * <td>capturing(string("C"))</td></tr>
 * </tbody></table></blockquote>
 * <p> Group zero always stands for the entire expression.
 * </p>
 * <p>There are some methods which can refer to CapturingGroups via this index, however passing in a reference
 * to the group itself should almost always be preferred to those methods.</p>
 */
public final class CapturingGroup extends RegexLiteral {

  private static final String LABEL_BASE = "label";
  private static final AtomicLong NEXT_LABEL_ID = new AtomicLong();
  final String label;

  /**
   * Gets a CapturingGroup capturing the concatenation of all passed in regex
   *
   * @param components The regexes to capture
   * @return a capturing group matching the same thing that the original regexes match
   */
  public static CapturingGroup capture(RegexLiteral... components) {
    if (components.length > 1) {
      return new CapturingGroup(LABEL_BASE + NEXT_LABEL_ID.incrementAndGet(), new Regex(components));
    } else {
      return new CapturingGroup(LABEL_BASE + NEXT_LABEL_ID.incrementAndGet(), components[0]);
    }
  }

  private CapturingGroup(String label, RegexLiteral r) {
    super(r, "(?<", label, ">", r.getRawRegex(), ")");

    // Add our group, shifting all other indexes by 1
    this.groupToIndex.replaceAll((group, index) -> index + 1);
    this.groupToIndex.put(this, 1);
    this.numGroups += 1;
    this.label = label;
  }

  @Override
  String selfAsGrouped() {
    return getRawRegex();
  }

}
