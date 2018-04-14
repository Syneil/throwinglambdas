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
 * Represents a function that accepts a long-valued argument and produces a result. This is the {@code long}-consuming
 * primitive specialization for {@link ThrowingFunction} and the throwing specialization of {@link LongFunction}.
 *
 * <p>This is a functional interface whose functional method is {@link #apply(long)}.
 *
 * @param <R> the type of the result of the function
 * @param <E> the type of exception that may be thrown
 * @see ThrowingFunction
 * @see LongFunction
 */
@FunctionalInterface
public interface ThrowingLongFunction<R, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe function
     * @param <R>    the type of the result of the function
     * @return a safe function that accepts a long and produces a result
     */
    static <R> LongFunction<R> protect(final ThrowingLongFunction<? extends R, Exception> lambda) {
        return value -> {
            try {
                return lambda.apply(value);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws E
     */
    R apply(long value) throws E;
}
