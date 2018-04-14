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
 * Represents an operation on a single {@code double}-valued operand that produces a {@code double}-valued result. This
 * is the primitive type specialization of {@link ThrowingUnaryOperator} for {@code double} and the throwing
 * specialization of {@link DoubleUnaryOperator}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsDouble(double)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingUnaryOperator
 * @see DoubleUnaryOperator
 */
@FunctionalInterface
public interface ThrowingDoubleUnaryOperator<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation that accepts a double and produces a double
     */
    static DoubleUnaryOperator protect(final ThrowingDoubleUnaryOperator<Exception> lambda) {
        return operand -> {
            try {
                return lambda.applyAsDouble(operand);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }
    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static ThrowingDoubleUnaryOperator identity() {
        return t -> t;
    }

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E
     */
    double applyAsDouble(double operand) throws E;

    /**
     * Returns a composed operator that first applies the {@code before} operator to its input, and then applies this
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before} operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #andThen(ThrowingDoubleUnaryOperator)
     */
    default ThrowingDoubleUnaryOperator<E> compose(final ThrowingDoubleUnaryOperator<? extends E> before) {
        Objects.requireNonNull(before);
        return v -> applyAsDouble(before.applyAsDouble(v));
    }

    /**
     * Returns a composed operator that first applies this operator to its input, and then applies the {@code after}
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(ThrowingDoubleUnaryOperator)
     */
    default ThrowingDoubleUnaryOperator<E> andThen(final ThrowingDoubleUnaryOperator<? extends E> after) {
        Objects.requireNonNull(after);
        return t -> after.applyAsDouble(applyAsDouble(t));
    }
}
