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
 * Represents a function that produces a double-valued result. This is the {@code double}-producing primitive
 * specialization for {@link ThrowingFunction} and the throwing specialization of {@link ToDoubleFunction}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsDouble(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of exception that may be thrown
 * @see ThrowingFunction
 * @see ToDoubleFunction
 */
@FunctionalInterface
public interface ThrowingToDoubleFunction<T, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe function
     * @param <T>    the type of the input to the function
     * @return a safe function that accepts one argument and produces a double
     */
    static <T> ToDoubleFunction<T> protect(final ThrowingToDoubleFunction<? super T, Exception> lambda) {
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
    double applyAsDouble(T value) throws E;
}
