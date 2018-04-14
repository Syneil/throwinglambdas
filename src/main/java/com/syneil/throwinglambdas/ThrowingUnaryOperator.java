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
 * Represents an operation on a single operand that produces a result of the same type as its operand. This is a
 * specialization of {@code ThrowingFunction} for the case where the operand and result are of the same type and the
 * throwing specialization of {@link UnaryOperator}.
 *
 * <p>This is a functional interface whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the operand and result of the operator
 * @param <E> the type of exception that may be thrown
 * @see ThrowingFunction
 * @see UnaryOperator
 */
@FunctionalInterface
public interface ThrowingUnaryOperator<T, E extends Exception> extends ThrowingFunction<T, T, E> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @param <T>    the type of the input to the operation
     * @return a safe operation that accepts one argument and produces a result of the same type
     */
    static <T> UnaryOperator<T> protect(final ThrowingUnaryOperator<T, Exception> lambda) {
        return t -> {
            try {
                return lambda.apply(t);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @param <E> the type of exception that may be thrown
     * @return a unary operator that always returns its input argument
     */
    static <T, E extends Exception> ThrowingUnaryOperator<T, E> identity() {
        return t -> t;
    }
}
