package com.github.ermay12.regex;

public final class CapturingGroup extends Regex {

    /**
     * Returns a regex which matches the same thing that the original regex matches, but now as a capturing group
     *
     * See: Capturing groups in the class documentation
     * @param r The regex to capture
     * @return a capturing group matching the same thing that the original regex matches
     */
    public static CapturingGroup capture(Regex r) {
        return new CapturingGroup(r);
    }
    private CapturingGroup(Regex regex) {
        super("(", regex.getRawRegex(), ")");

        // We are the only group
        this.groupToIndex.put(this, 1);
    }

    @Override
    String selfAsGrouped() {
            return getRawRegex();
        }
}
