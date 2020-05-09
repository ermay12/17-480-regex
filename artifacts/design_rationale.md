# Design Rationale

## Composition Pattern
While developing the API, we were debating whether to have the regexs be constructed with the Builder Pattern or the Composite Pattern. We ultimately decided to use the Composite Pattern, because regexs are inherently compositional, in that a regex can be composed of various smaller regexs. The Composite Pattern works by having the regex constructors take in any amount of parameters. These parameters could either be made up of some number of other Regexes or some number of CharSequences. Using the Composite Pattern also makes it easier for the user to extract matches for specific Capturing Groups; users can initialize the Capturing Group outside of the Regex constructor and reference the same Capturing Group when trying to extract matches from it.

## Static Factories
For the regex constructs, we decided to use static factories rather than constructors because there are many ways to construct these objects. Without explicit names for each constructor, a generic constructor could be misleading. For example, you can create a regex from a raw regex, or you can create one from a string. `public Regex(String s)` is ambiguous here.

## Converting Between Native JAVA Types and a Regex

In order to provide smooth usage for users, we decided to overload all of the Regex methods to both take in a native JAVA type (such as String or char) and a regex. We had to then consider wether to escape the characters passed in (when given a native JAVA type). We decided to escape them so as to abstract away Regex idiosyncracies.

Initially, for many of these methods (such as anyAmount), we restricted the type passed in to be a char (as `abc*` only allows `c` to be repeated). Eventually we decided to allow any string to be passed in, as this is a Regex idiosyncracy and it was already possible to pass in a string to these methods by wrapping it in a regex (e.g. `anyAmount(string("abc"))`). Thus, running `anyAmount("abc")` would produce (`(?abc)*`) to hide away the restriction of regexes.

This also led to a convenient meta-feature of our API, which is that `String`s (or really - `CharSequence`s) are always interchangeable with Regex-es, and `char`s are always interchangeable with `CharacterClass`es

## (Named) Capturing Groups
During development we had to consider what to do about capturing groups, and more specifically named capturing groups. Named capturing groups are a dangerous aspect of the original Regex API, as they rely on string labels. String labels first of all suffer from the problem of easy naming collisions. Secondly, the API restricts the possible labels by requiring that they be formed of alphanumeric characters starting with a letter. For both of these types of problems, the original API will catch the issue at runtime, throwing an error.

We decided to replace string labeled, named capturing groups with a CapturingGroup object. This takes advantage of JAVA's built in collision detection for variable names to throw errors at compile-time rather than at runtime. Furthermore, by using variable names, we allow JAVA to enforce constraints (like what characters are allowed), once again at compile time. This has both the advantage of hiding regex idiosyncracies (e.g. what characters are allowed) and bringing most errors to compile-time.

Unfortunately, although CapturingGroup objects reduce the likelihood of users passing in the same label twice in a regex, they don't eliminate the possibility. Thus, we were left with the choice as to what to do in this case. We briefly considered following in the Regex library's footsteps and throwing an error at runtime. The issue here is that there are valid reasons why a user might want to reuse the same capturing group. For instance, in the below code, the same capturing group is used multiple times, but the user code is logically correct.

```
Regex d = concatenate(capture(DIGIT), WHITESPACE);
Regex all = concatenate(d, d, d, d);
RegexMatch m = all.firstMatch("1 3 5 7").get();
int sum = m.group(1) + m.group(2) + m.group(3) + m.group(4);
```

We also considered having a method to return all instances where a group is being matched. We eventually decided against it as this seemed to imply (to users who have no experience with Regex'es) that calling that method on `anyAmount(capture(DIGIT))` would give you all of the digits. This is counter to how it actually works (which is only giving you the last occurence). With that idea in mind, we settled on the final solution - if a capturing group object is used multiple times in the same regex, getting the string that it matches gives you the last instance of the capturing group. We feel this blends in nicely with the semantics of other regex constructs.

## Matching
We implemented four functions for checking for matches: `doesMatch()`, `firstMatch()`, `getMatch()`, and `getMatches()`. `doesMatch()` returns a `boolean` of whether the input string matches the regex. `firstMatch()` gets the first section of the input that matches the regex. We decided to have `firstMatch()` return an `Optional`, so that when there is no valid match between the string and the regex, `isPresent()` will return `false`. We debated over whether to have the `firstMatch()` return `null` when there is no valid match, but we ended up having it return an `Optional` to force the user to validate that the regex matched.

`getMatch()` takes in a string input and an integer index. `getMatches()` returns a `Stream` of `RegexMatch`es. We decided to use a `Stream` rather than a `List` so that if the input string is very large, if the computation will not hang for a long time. With the `Stream`, the user can iterate through all the valid matches.

## Replacement
If a user would like to implement replacement, they could use the API's `ReplacementRegex` class. `Regex.replace()` takes in the string input and the `ReplacementRegex`, and will return a string of the input with the replacement implemented. We also figured that, in the case of multiple matches, a user might want to only replace the matches on certain conditions. We thus added a convenience method that overloads `replace()` by taking in a function rather than a `ReplacementRegex`. This function could condition on each match and decide how to perform the replacement accordingly.

## Other Convenience Methods
After conducting some user testing, we decided to add some convenience methods (such as `repeatAtMost()`) that do not directly translate to regex constructs.

## Naming
During our development of this API, we had a lot of discourse over the naming of certain classes, methods, and fields. Our goal was to maximize  understandability, while also avoiding unnecessary verbosity.
