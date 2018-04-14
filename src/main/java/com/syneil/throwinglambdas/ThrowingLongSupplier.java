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
 * Represents a supplier of {@code long}-valued results. This is the {@code long}-producing primitive specialization of
 * {@link ThrowingSupplier}.
 *
 * <p>There is no requirement that a distinct result be returned each time the supplier is invoked.
 *
 * <p>This is a functional interface whose functional method is {@link #getAsLong()}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingSupplier
 * @see LongSupplier
 */
@FunctionalInterface
public interface ThrowingLongSupplier<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe supplier
     * @return a safe supplier of longs
     */
    static LongSupplier protect(final ThrowingLongSupplier<Exception> lambda) {
        return () -> {
            try {
                return lambda.getAsLong();
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E
     */
    long getAsLong() throws E;
}
