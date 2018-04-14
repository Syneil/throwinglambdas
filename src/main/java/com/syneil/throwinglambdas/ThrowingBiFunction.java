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
 * Represents a function that accepts two arguments and produces a result. This is the two-arity specialization of
 * {@link ThrowingFunction} and the throwing specialization of {@link BiFunction}.
 *
 * <p>This is a functional interface whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception that may be thrown
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingBiFunction<T, U, R, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe function
     * @param <T>    the type of the first argument to the function
     * @param <U>    the type of the second argument to the function
     * @param <R>    the type of the result of the function
     * @return a safe function that accepts two arguments and produces a result
     */
    static <T, U, R> BiFunction<T, U, R> protect(
            final ThrowingBiFunction<? super T, ? super U, ? extends R, Exception> lambda) {
        return (t, u) -> {
            try {
                return lambda.apply(t, u);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E
     */
    R apply(T t, U u) throws E;

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function as the cause of a {@link LambdaException}.
     *
     * @param <V>   the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default <V> ThrowingBiFunction<T, U, V, E> andThen(final ThrowingFunction<? super R, ? extends V, ? extends E> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
