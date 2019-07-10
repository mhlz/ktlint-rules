# ktlint-rules

These are just some personal ktlint rules that I've been using for a while, mostly based on things that I saw (and didn't like) working with Kotlin.

## Usage

You can use this ruleset using Jitpack as described in the ktlint documentation.

## no-empty-line-after-function-definition

This rule disallows putting an empty line immediately after a function definition:

```kotlin
// not allowed
fun test() {

  println("hello")
}
```

The idea behind this is to cut down on unnecessary whitespace to keep the files smaller.

## no-null-assertion

Disallows using the `!!` operator, instead forcing you to rely on proper error handling (or at least not just throwing an NPE but an actual nice exception).

## use-named-parameters

If you're calling a function with more than 4 parameters, you should use named parameters. This is done for additional clarity, especially when it comes to data class constructors. Unfortunately, ktlint cannot detect varargs which means some functions (such as listOf etc) are excluded. I'm more than happy to accept PRs with more functions to exclude.

```kotlin
// not allowed
val dat = DataClass(
  1,
  2,
  3,
  4,
  5
)
```

