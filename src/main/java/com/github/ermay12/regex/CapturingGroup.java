package com.github.ermay12.regex;

import java.util.concurrent.atomic.AtomicLong;

public final class CapturingGroup extends Regex {

    private static final String LABEL_BASE = "label";
    private static final AtomicLong NEXT_LABEL_ID = new AtomicLong();
    final String label;

    /**
     * Returns a regex which matches the same thing that the original regex matches, but now as a capturing group
     *
     * See: Capturing groups in the class documentation
     * @param r The regex to capture
     * @return a capturing group matching the same thing that the original regex matches
     */
    public static CapturingGroup capture(Regex r) {
        return new CapturingGroup(LABEL_BASE + NEXT_LABEL_ID.incrementAndGet(), r);
    }
    private CapturingGroup(String label, Regex r) {
        super("(?<", label, ">", r.getRawRegex(), ")");

        // We are the only group
        this.groupToIndex.put(this, 1);
        this.numGroups = 1;
        this.label = label;
    }

    @Override
    String selfAsGrouped() {
            return getRawRegex();
        }
}
