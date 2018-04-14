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
 * Represents an operation that accepts a single {@code long}-valued argument and returns no result. This is the
 * primitive type specialization of {@link ThrowingConsumer} for {@code long} and the throwing specialization of
 * {@link LongConsumer}. Unlike most other functional interfaces, {@code ThrowingLongConsumer} is expected to operate
 * via side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(long)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingConsumer
 * @see LongConsumer
 */
@FunctionalInterface
public interface ThrowingLongConsumer<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation that accepts one long and returns no result
     */
    static LongConsumer protect(final ThrowingLongConsumer<Exception> lambda) {
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
    void accept(long value) throws E;

    /**
     * Returns a composed {@code ThrowingLongConsumer} that performs, in sequence, this operation followed by the
     * {@code after} operation. If performing either operation throws an exception, it is relayed to the caller of the
     * composed operation. If performing this operation throws an exception, the {@code after} operation will not be
     * performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingLongConsumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingLongConsumer<E> andThen(final ThrowingLongConsumer<? extends E> after) {
        Objects.requireNonNull(after);
        return t -> {
            accept(t);
            after.accept(t);
        };
    }
}
