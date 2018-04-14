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
 * Represents an operation upon two {@code int}-valued operands and producing an {@code int}-valued result. This is the
 * primitive type specialization of {@link ThrowingBinaryOperator} for {@code int} and the throwing specialization of
 * {@link IntBinaryOperator}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsInt(int, int)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingBinaryOperator
 * @see ThrowingIntUnaryOperator
 * @see IntBinaryOperator
 */
@FunctionalInterface
public interface ThrowingIntBinaryOperator<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation upon two ints producing an int
     */
    static IntBinaryOperator protect(final ThrowingIntBinaryOperator<Exception> lambda) {
        return (left, right) -> {
            try {
                return lambda.applyAsInt(left, right);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws E
     */
    int applyAsInt(int left, int right) throws E;
}
