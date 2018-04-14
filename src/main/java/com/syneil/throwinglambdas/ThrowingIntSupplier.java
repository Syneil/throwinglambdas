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
 * Represents a supplier of {@code int}-valued results. This is the {@code int}-producing primitive specialization of
 * {@link ThrowingSupplier} and the throwing specialization of {@link IntSupplier}.
 *
 * <p>There is no requirement that a distinct result be returned each time the supplier is invoked.
 *
 * <p>This is a functional interface whose functional method is {@link #getAsInt()}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingSupplier
 * @see IntSupplier
 */
@FunctionalInterface
public interface ThrowingIntSupplier<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe supplier
     * @return a safe supplier of ints
     */
    static IntSupplier protect(final ThrowingIntSupplier<Exception> lambda) {
        return () -> {
            try {
                return lambda.getAsInt();
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
    int getAsInt() throws E;
}
