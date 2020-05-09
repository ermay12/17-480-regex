# Design Rationale

## Composition Pattern
While developing the API, we were debating whether to have the regexs be constructed with the Builder Pattern or the Composite Pattern. We ultimately decided to use the Composite Pattern, because regexs are inherently compositional, in that a regex can be composed of various smaller regexs. The Composite Pattern works by having the regex constructors take in any amount of parameters. These parameters could be made up of some number of character sequences, some number of regexs, or a single regex and some number of character sequences. Using the Composite Pattern also makes it easier for the user to extract matches for specific Capturing Groups; users can initialize the Capturing Group outside of the Regex constructor and reference the same Capturing Group when trying to extract matches from it.

## Static Factories
For the regex constructs, we decided to use static factories rather than constructors because there are many ways to construct these objects. Without explicit names for each constructor, a generic constructor could be misleading. For example, you can create a regex from a raw regex, or you can create one from a string. `public Regex(String s)` is ambiguous here.

## Matching
We implemented four functions for checking for matches: `doesMatch()`, `firstMatch()`, `getMatch()`, and `getMatches()`. `doesMatch()` returns a `boolean` of whether the input string matches the regex. `firstMatch()` gets the first section of the input that matches the regex. We decided to have `firstMatch()` return an `Optional`, so that when there is no valid match between the string and the regex, `isPresent()` will return `false`. We debated over whether to have the `firstMatch()` return `null` when there is no valid match, but we ended up having it return an `Optional` to force the user to validate that the regex matched.

`getMatch()` takes in a string input and an integer index. `getMatches()` returns a `Stream` of `RegexMatch`es. We decided to use a `Stream` rather than a `List` so that if the input string is very large, if the computation will not hang for a long time. With the `Stream`, the user can iterate through all the valid matches.

## Replacement
If a user would like to implement replacement, they could use the API's `ReplacementRegex` class. `Regex.replace()` takes in the string input and the `ReplacementRegex`, and will return a string of the input with the replacement implemented. We also figured that, in the case of multiple matches, a user might want to only replace the matches on certain conditions. We thus added a convenience method that overloads `replace()` by taking in a function rather than a `ReplacementRegex`. This function could condition on each match and decide how to perform the replacement accordingly.

## Other Convenience Methods
After conducting some user testing, we decided to add some convenience methods (such as `repeatAtMost()`) that do not directly translate to regex constructs.

## Naming
During our development of this API, we had a lot of discourse over the naming of certain classes, methods, and fields. Our goal was to maximize  understandability, while also avoiding unnecessary verbosity.