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
 * Represents an operation on a single {@code long}-valued operand that produces a {@code long}-valued result. This is
 * the primitive type specialization of {@link ThrowingUnaryOperator} for {@code long} and the throwing specialization
 * of {@link LongUnaryOperator}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsLong(long)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingUnaryOperator
 * @see LongUnaryOperator
 */
@FunctionalInterface
public interface ThrowingLongUnaryOperator<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation on a single long producing a long
     */
    static LongUnaryOperator protect(final ThrowingLongUnaryOperator<Exception> lambda) {
        return operand -> {
            try {
                return lambda.applyAsLong(operand);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <E> the type of exception that may be thrown
     * @return a unary operator that always returns its input argument
     */
    static <E extends Exception> ThrowingLongUnaryOperator<E> identity() {
        return t -> t;
    }

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E
     */
    long applyAsLong(long operand) throws E;

    /**
     * Returns a composed operator that first applies the {@code before} operator to its input, and then applies this
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before} operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #andThen(ThrowingLongUnaryOperator)
     */
    default ThrowingLongUnaryOperator<E> compose(final ThrowingLongUnaryOperator<? extends E> before) {
        Objects.requireNonNull(before);
        return v -> applyAsLong(before.applyAsLong(v));
    }

    /**
     * Returns a composed operator that first applies this operator to its input, and then applies the {@code after}
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(ThrowingLongUnaryOperator)
     */
    default ThrowingLongUnaryOperator<E> andThen(final ThrowingLongUnaryOperator<? extends E> after) {
        Objects.requireNonNull(after);
        return t -> after.applyAsLong(applyAsLong(t));
    }
}
