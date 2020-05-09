![Java CI with Gradle](https://github.com/ermay12/17-480-regex/workflows/Java%20CI%20with%20Gradle/badge.svg)

Final report found [here](https://docs.google.com/document/d/1RIbGxl4W1GHWGpPrQPv2mAbvK5lqsqRVA_fLOTqZiYQ/edit?usp=sharing)

# 17-480-regex
17-480 Final Project: redux of java.util.regex

To add a dependency on 17-480-regex using Maven, use the following:
```
<dependency>
  <groupId>com.github.ermay12</groupId>
  <artifactId>17-480-regex</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```


Example usage:

```java
/*
import static com.github.ermay12.regex.Regex.*;
import static com.github.ermay12.regex.CapturingGroup.*;
import static com.github.ermay12.regex.CharacterClass.*;
import static org.junit.Assert.*;
import java.util.Optional;
*/

CapturingGroup nameGroup = capture(anyAmount(not(character('@'))));
CapturingGroup domainGroup = capture(anyAmount(not(character('.'))), string("."), anyAmount(WILDCARD));

Regex regex = concatenate(nameGroup, string("@"), domainGroup);

String input = "hello@example.com";

Optional<RegexMatch> result = regex.firstMatch(input);
assert (result.isPresent());
assertEquals("hello", result.get().group(nameGroup));
assertEquals("example.com", result.get().group(domainGroup));
```

Check our test files for more examples of how to use this api.
