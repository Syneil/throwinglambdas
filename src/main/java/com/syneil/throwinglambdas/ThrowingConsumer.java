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
 * Represents an operation that accepts a single input argument and returns no result. Unlike most other functional
 * interfaces, {@code ThrowingConsumer} is expected to operate via side-effects.
 *
 * <p>This is a functional interface whose functional method is {@link #accept(Object)}.
 *
 * @param <T> the type of the input to the operation
 * @param <E> the type of exception that may be thrown
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @param <T>    the type of the input to the operation
     * @return a safe operation that accepts one argument and returns no result
     */
    static <T> Consumer<T> protect(final ThrowingConsumer<? super T, Exception> lambda) {
        return t -> {
            try {
                lambda.accept(t);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }


    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws E
     */
    void accept(T t) throws E;

    /**
     * Returns a composed {@code ThrowingConsumer} that performs, in sequence, this operation followed by the
     * {@code after} operation. If performing either operation throws an exception, it is relayed to the caller of the
     * composed operation as the cause of a {@link LambdaException}. If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingConsumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingConsumer<T, E> andThen(final ThrowingConsumer<? super T, ? extends E> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
