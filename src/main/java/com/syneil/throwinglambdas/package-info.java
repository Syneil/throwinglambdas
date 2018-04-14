/**
 * This package contains throwing specializations of the functional interfaces of the {@code java.util.function}
 * package.
 * <p>
 * Also included is a throwing specialization of {@link java.lang.Runnable} and the
 * {@link com.syneil.throwinglambdas.LambdaException}, which is a {@link java.lang.RuntimeException}, intended to wrap
 * caught exceptions in order to satisfy the original functional method signatures.
 * <p>
 * Instances of these interfaces are not intended to be implemented explicitly. Instead, each interface offers a static
 * {@code protect(lambda)} method that returns an instance of the equivalent interface from {@code java.util.function}
 * (or {@code java.lang.Runnable}).
 * <p>
 * For example, a {@link java.util.function.Function Function} must accept one parameter and not throw any checked
 * exceptions. Therefore many common I/O operations (such as {@link java.io.InputStream#read InputStream.read()}) are
 * not valid implementations as they throw the checked {@link java.io.IOException IOException}.
 * <p>
 * So where it may be desirable to use a reference to such a method (that is, {@code InputStream::read}) this package
 * lets the consumer write instead {@code ThrowingFunction.protect(InputStream::read)}.
 * <p>
 * Any checked exception thrown by the original method will instead be thrown as a runtime
 * {@link com.syneil.throwinglambdas.LambdaException} with the original as its {@link java.lang.Throwable#cause cause}.
 */
package com.syneil.throwinglambdas;
