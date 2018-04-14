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

import java.util.*;
import java.util.function.*;

/**
 * Represents an operation that accepts two input arguments and returns no result. This is the two-arity specialization
 * of {@link ThrowingConsumer} and the throwing specialization of {@link BiConsumer}. Unlike most other functional
 * interfaces, {@code ThrowingBiConsumer} is expected to operate via side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(Object, Object)}.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <E> the type of exception that may be thrown
 * @see ThrowingConsumer
 * @see BiConsumer
 */
@FunctionalInterface
public interface ThrowingBiConsumer<T, U, E extends Exception> {

    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @param <T>    the type of the first argument to the operation
     * @param <U>    the type of the second argument to the operation
     * @return a safe operation that accepts two input arguments and returns no result
     */
    static <T, U> BiConsumer<T, U> protect(final ThrowingBiConsumer<? super T, ? super U, Exception> lambda) {
        return (t, u) -> {
            try {
                lambda.accept(t, u);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Performs this operation on the given arguments, possibly throwing an exception.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @throws E
     */
    void accept(T t, U u) throws E;

    /**
     * Returns a composed {@code ThrowingBiConsumer} that performs, in sequence, this operation followed by the
     * {@code after} operation. If performing either operation throws an exception, it is relayed to the caller of the
     * composed operation as the cause of a {@link LambdaException}. If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingBiConsumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingBiConsumer<T, U, E> andThen(final ThrowingBiConsumer<? super T, ? super U, ? extends E> after) {
        Objects.requireNonNull(after);

        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}
