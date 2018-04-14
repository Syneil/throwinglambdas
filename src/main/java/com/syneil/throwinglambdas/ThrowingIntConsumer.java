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
 * Represents an operation that accepts a single {@code int}-valued argument and returns no result. This is the
 * primitive type specialization of {@link ThrowingConsumer} for {@code int} and the throwing specialization of
 * {@link IntConsumer}. Unlike most other functional interfaces, {@code ThrowingIntConsumer} is expected to operate via
 * side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(int)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingConsumer
 * @see IntConsumer
 */
@FunctionalInterface
public interface ThrowingIntConsumer<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation that accepts two ints and returns no result
     */
    static IntConsumer protect(final ThrowingIntConsumer<Exception> lambda) {
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
    void accept(int value) throws E;

    /**
     * Returns a composed {@code ThrowingIntConsumer} that performs, in sequence, this operation followed by the
     * {@code after} operation. If performing either operation throws an exception, it is relayed to the caller of the
     * composed operation. If performing this operation throws an exception, the {@code after} operation will not be
     * performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingIntConsumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingIntConsumer<E> andThen(final ThrowingIntConsumer<? extends E> after) {
        Objects.requireNonNull(after);
        return t -> {
            accept(t);
            after.accept(t);
        };
    }
}
