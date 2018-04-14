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
 * Represents a supplier of results.
 *
 * <p>There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 *
 * <p>This is a functional interface whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this supplier
 * @param <E> the type of exception that may be thrown
 * @see Supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe supplier
     * @param <T>    the type of the results supplied by this supplier
     * @return a safe supplier of results
     */
    static <T> Supplier<T> protect(final ThrowingSupplier<? extends T, Exception> lambda) {
        return () -> {
            try {
                return lambda.get();
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
    T get() throws E;
}
