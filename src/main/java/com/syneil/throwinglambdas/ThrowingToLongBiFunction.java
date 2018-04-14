/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.function.*;

/**
 * Represents a function that accepts two arguments and produces a long-valued result. This is the
 * {@code long}-producing primitive specialization for {@link ThrowingBiFunction} and the throwing specialization of
 * {@link ToLongBiFunction}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsLong(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception that may be thrown
 *
 * @see ThrowingBiFunction
 * @see ToLongBiFunction
 */
@FunctionalInterface
public interface ThrowingToLongBiFunction<T, U, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe function
     * @param <T>    the type of the first argument to the function
     * @param <U>    the type of the second argument to the function
     * @return a safe function that accepts two arguments and produces a long
     */
    static <T, U> ToLongBiFunction<T, U> protect(final ThrowingToLongBiFunction<? super T, ? super U, Exception> lambda) {
        return (t, u) -> {
            try {
                return lambda.applyAsLong(t, u);
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
    long applyAsLong(T t, U u) throws E;
}
