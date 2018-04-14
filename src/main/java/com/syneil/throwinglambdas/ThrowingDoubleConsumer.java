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
 * Represents an operation that accepts a single {@code double}-valued argument and returns no result. This is the
 * primitive type specialization of {@link ThrowingConsumer} for {@code double} and the throwing specialization of
 * {@link DoubleConsumer}. Unlike most other functional interfaces, {@code ThrowingDoubleConsumer} is expected to
 * operate via side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(double)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingConsumer
 * @see DoubleConsumer
 */
@FunctionalInterface
public interface ThrowingDoubleConsumer<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation that accepts one double and returns no result
     */
    static DoubleConsumer protect(final ThrowingDoubleConsumer<Exception> lambda) {
        return value -> {
            try {
                lambda.accept(value);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws E
     */
    void accept(double value) throws E;

    /**
     * Returns a composed {@code ThrowingDoubleConsumer} that performs, in sequence, this operation followed by the
     * {@code after} operation. If performing either operation throws an exception, it is relayed to the caller of the
     * composed operation. If performing this operation throws an exception, the {@code after} operation will not be
     * performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingDoubleConsumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingDoubleConsumer<E> andThen(final ThrowingDoubleConsumer<? extends E> after) {
        Objects.requireNonNull(after);
        return t -> {
            accept(t);
            after.accept(t);
        };
    }
}
