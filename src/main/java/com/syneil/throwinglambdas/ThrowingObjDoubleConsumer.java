/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Represents an operation that accepts an object-valued and a {@code double}-valued argument, and returns no result.
 * This is the {@code (reference, double)} specialization of {@link ThrowingBiConsumer} and the throwing specialization
 * of {@link ObjDoubleConsumer}. Unlike most other functional interfaces, {@code ThrowingObjDoubleConsumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(Object, double)}.
 * @param <T> the type of the object argument to the operation
 * @param <E> the type of exception that may be thrown
 * @see ThrowingBiConsumer
 * @see ObjDoubleConsumer
 */
@FunctionalInterface
public interface ThrowingObjDoubleConsumer<T, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @param <T>    the type of the object argument to the operation
     * @return a safe operation that accepts an object and a double and returns no result
     */
    static <T> ObjDoubleConsumer<T> protect(final ThrowingObjDoubleConsumer<? super T, Exception> lambda) {
        return (t, value) -> {
            try {
                lambda.accept(t, value);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     * @throws E
     */
    void accept(T t, double value) throws E;
}
