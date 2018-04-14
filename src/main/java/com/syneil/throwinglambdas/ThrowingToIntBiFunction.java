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
 * Represents a function that accepts two arguments and produces an int-valued result. This is the
 * {@code int}-producing primitive specialization for {@link ThrowingBiFunction} and the throwing specialization of
 * {@link ToIntBiFunction}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsInt(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception that may be thrown
 * @see ThrowingBiFunction
 * @see ToIntBiFunction
 */
@FunctionalInterface
public interface ThrowingToIntBiFunction<T, U, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe function
     * @param <T>    the type of the first argument to the function
     * @param <U>    the type of the second argument to the function
     * @return a safe function that accepts two arguments and produces an int
     */
    static <T, U> ToIntBiFunction<T, U> protect(final ThrowingToIntBiFunction<? super T, ? super U, Exception> lambda) {
        return (t, u) -> {
            try {
                return lambda.applyAsInt(t, u);
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
    int applyAsInt(T t, U u) throws E;
}
