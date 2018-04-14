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
 * Represents an operation on a single {@code int}-valued operand that produces an {@code int}-valued result. This is
 * the primitive type specialization of {@link ThrowingUnaryOperator} for {@code int} and the throwing specialization
 * of {@link IntUnaryOperator}.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsInt(int)}.
 *
 * @param <E> the type of exception that may be thrown
 * @see ThrowingUnaryOperator
 * @see IntUnaryOperator
 */
@FunctionalInterface
public interface ThrowingIntUnaryOperator<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation that accepts an int and produces an int
     */
    static IntUnaryOperator protect(final ThrowingIntUnaryOperator<Exception> lambda) {
        return operand -> {
            try {
                return lambda.applyAsInt(operand);
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
    static <E extends Exception> ThrowingIntUnaryOperator<E> identity() {
        return t -> t;
    }

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     * @throws E
     */
    int applyAsInt(int operand) throws E;

    /**
     * Returns a composed operator that first applies the {@code before} operator to its input, and then applies this
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before} operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #andThen(ThrowingIntUnaryOperator)
     */
    default ThrowingIntUnaryOperator<E> compose(final ThrowingIntUnaryOperator<? extends E> before) {
        Objects.requireNonNull(before);
        return v -> applyAsInt(before.applyAsInt(v));
    }

    /**
     * Returns a composed operator that first applies this operator to its input, and then applies the {@code after}
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(ThrowingIntUnaryOperator)
     */
    default ThrowingIntUnaryOperator<E> andThen(final ThrowingIntUnaryOperator<? extends E> after) {
        Objects.requireNonNull(after);
        return t -> after.applyAsInt(applyAsInt(t));
    }
}
