/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.syneil.throwinglambdas;

import java.util.*;
import java.util.function.*;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * <p>This is a functional interface whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception that may be thrown
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @param <T>    the type of the first argument to the operation
     * @param <R>    the type of the result of the operation
     * @return a safe operation that accepts one argument and produces a result
     */
    static <T, R> Function<T, R> protect(final ThrowingFunction<? super T, ? extends R, Exception> lambda) {
        return t -> {
            try {
                return lambda.apply(t);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @param <E> the type of exception that may be thrown
     * @return a function that always returns its input argument
     */
    static <T, E extends Exception> ThrowingFunction<T, T, E> identity() {
        return t -> t;
    }

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws E
     */
    R apply(T t) throws E;

    /**
     * Returns a composed function that first applies the {@code before} function to its input, and then applies this
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before} function and then applies this function
     * @throws NullPointerException if before is null
     * @see #andThen(ThrowingFunction)
     */
    default <V> ThrowingFunction<V, R, E> compose(final ThrowingFunction<? super V, ? extends T, ? extends E> before) {
        Objects.requireNonNull(before);
        return v -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see #compose(ThrowingFunction)
     */
    default <V> ThrowingFunction<T, V, E> andThen(final ThrowingFunction<? super R, ? extends V, ? extends E> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(apply(t));
    }
}
