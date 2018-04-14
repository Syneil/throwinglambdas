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
 * Represents an operation upon two {@code long}-valued operands and producing a {@code long}-valued result. This is
 * the primitive type specialization of {@link ThrowingBinaryOperator} for {@code long} and the throwing specialization
 * of {@link LongBinaryOperator}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsLong(long, long)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingBinaryOperator
 * @see ThrowingLongUnaryOperator
 * @see LongBinaryOperator
 */
@FunctionalInterface
public interface ThrowingLongBinaryOperator<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation upon two longs producing a long
     */
    static LongBinaryOperator protect(final ThrowingLongBinaryOperator<Exception> lambda) {
        return (left, right) -> {
            try {
                return lambda.applyAsLong(left, right);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Applies this operator to the given operands.
     *
     * @param left the first operand
     * @param right the second operand
     * @return the operator result
     * @throws E
     */
    long applyAsLong(long left, long right) throws E;
}
