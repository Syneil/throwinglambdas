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
 * Represents a function that accepts an int-valued argument and produces a long-valued result. This is the
 * {@code int}-to-{@code long} primitive specialization for {@link ThrowingFunction} and the throwing specialization of
 * {@link IntToLongFunction}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsLong(int)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingFunction
 * @see IntToLongFunction
 */
@FunctionalInterface
public interface ThrowingIntToLongFunction<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe function
     * @return a safe function that accepts an int and produces a long
     */
    static IntToLongFunction protect(final ThrowingIntToLongFunction<Exception> lambda) {
        return value -> {
            try {
                return lambda.applyAsLong(value);
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
    long applyAsLong(int value) throws E;
}
