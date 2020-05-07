package com.github.ermay12.regex;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A CapturingGroup represents a subsection of a larger Regex which you can extract matches from.
 * For instance, if you wanted a regex to match an email such as "test@example.com", you could use
 * two CapturingGroups to "capture" both the name (test) and the domain (example.com) and separate that
 * out. An example of this is:
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
 *
 * If the same CapturingGroup is present in a regex multiple times, the last match will be used as the value.
 * This also applies, for instance, if the CapturingGroup is in an "anyAmount", as the last match from that
 * will be the value.
 */
public final class CapturingGroup extends Regex {

    private static final String LABEL_BASE = "label";
    private static final AtomicLong NEXT_LABEL_ID = new AtomicLong();
    final String label;

    /**
     * Gets a CapturingGroup capturing the concatenation of all passed in regex
     *
     * @param components The regexes to capture
     * @return a capturing group matching the same thing that the original regexes match
     */
    public static CapturingGroup capture(Regex... components) {
        return new CapturingGroup(LABEL_BASE + NEXT_LABEL_ID.incrementAndGet(), new Regex(components));
    }
    private CapturingGroup(String label, Regex r) {
        super("(?<", label, ">", r.getRawRegex(), ")");

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
