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
 * Represents a function that accepts a long-valued argument and produces a double-valued result. This is the
 * {@code long}-to-{@code double} primitive specialization for {@link ThrowingFunction} and the throwing specialization
 * of {@link LongToDoubleFunction}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsDouble(long)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingFunction
 * @see LongToDoubleFunction
 */
@FunctionalInterface
public interface ThrowingLongToDoubleFunction<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe function
     * @return a safe function that accepts a long and produces a double
     */
    static LongToDoubleFunction protect(final ThrowingLongToDoubleFunction<Exception> lambda) {
        return value -> {
            try {
                return lambda.applyAsDouble(value);
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
    double applyAsDouble(long value) throws E;
}
