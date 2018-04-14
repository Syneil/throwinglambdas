# throwinglambdas
Library to help with Java 8+ lambda expressions that throw checked exceptions.

## Introduction
This project contains throwing specializations of the functional interfaces of the `java.util.function` package.

Also included is a throwing specialization of `java.lang.Runnable` and the `com.syneil.throwinglambdas.LambdaException`, which is a `java.lang.RuntimeException`, intended to wrap caught exceptions in order to satisfy the original functional method signatures.

Instances of these interfaces are not intended to be implemented explicitly. Instead, each interface offers a static `protect(lambda)` method that returns an instance of the equivalent interface from `java.util.function` (or `java.lang.Runnable`).

For example, a `Function` must accept one parameter and not throw any checked exceptions. Therefore many common I/O operations (such as `InputStream.read()`) are not valid implementations as they throw the checked `java.io.IOException`.

So where it may be desirable to use a reference to such a method (that is, `InputStream::read`) this package lets the consumer write instead `ThrowingFunction.protect(InputStream::read)`.

Any checked exception thrown by the original method will instead be thrown as a runtime `LambdaException` with the original as its cause.

## Examples
TBD
