# Requirements Document

## Clients and Use Cases
This API is intended to be used by anyone who would like implement Regex building and matching in their Java code, especially those who may be unfamiliar with the Regex language. It ultimately serves to be a redux of the `java.util.regex` package.

## Regex Patterns and Matching
A user should be able to build any pattern that can be compiled into a `java.util.regex.Pattern`. A user should also be able to get matches to that pattern with a String, by iterating or indexing to a `Stream`.

## Malformed Regexs
The API should either be able to handle malformed Regexs, or make it impossible for the user to create malformed Regexs. In the case of the former, a client's program should fail during compilation.

## No Matches
The API should provide a way for the user to check if a String has no matches with a Regex.

## Readability
The client code of this API should more readable than Java's existing API. This requirement should be benchmarked by having real programmers read example code and asking them how readable they found it to be.

## Approachability
This API should be more approachable than Java's existing API. This requirement should be benchmarked by having real programmers use the API and asking them how approachable they found it to be.

### Abstract away Regex idiosyncracies
In order to improve approachability, this API should abstract away Regex idiosyncracies, such as requiring certain objects to be wrapped in a capturing or non-capturing group.

## Verbosity
Although client code of this API will inevitably more verbose than the original API, the API should avoid unnecessary verbosity its naming and use.

### Don't force users to use `import static` in order to have code that is not overly verbose
As a minor subgoal to verbosity, client code of this API should still be readable and not overly verbose even without the use of `import static`

## Documentation
Each class, method, and field of the API must be clearly documented, especially keeping in mind those who may be unfamiliar with Regex terms and concepts.
