package com.github.ermay12.regex;

/**
 * This enum spcifies how to evaluate quantifiers on a regular expression.
 * Depending on this value, quantifiers may behave differently
 */
public enum EvaluationMethod {
    /**
     * Evaluate the quantifier in a greedy fashion. This matches the maximum amount of characters
     * possible while still maintaining a match.
     *
     * This evaluation type is the default.
     */
    GREEDILY,

    /**
     * Evaluate the quantifier in a possessive fashion. This matches the maximum amount of characters possible
     * regardless of if that maintains the match.
     * <br></br>
     * Warning: This evaluation type may lead to failing to match a string that other quantifiers would match.
     *          For example, <code>concatenate(anyAmount(WILDCARD, GREEDILY), string("a"))</code> will match "bba", but
     *          <code>concatenate(anyAmount(WILDCARD, POSSESSIVELY), string("a")) will not match "bba".
     */
    POSSESSIVELY,

    /**
     * Evaluate the quantifier in a lazy fashion. This matches the minimum amount of characters possible while
     * still maintaining a match.
     *
     * Note: The JAVA regex library calls this evaluation method "reluctant" evaluation
     */
    LAZILY;

    String toRegex() {
        switch(this) {
            case GREEDILY:
                return "";
            case POSSESSIVELY:
                return "+";
            case LAZILY:
                return "?";
        }
        return "";
    }
}
