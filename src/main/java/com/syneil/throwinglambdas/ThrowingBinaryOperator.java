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
 * Represents an operation upon two operands of the same type, producing a result of the same type as the operands.
 * This is a specialization of {@link ThrowingBiFunction} for the case where the operands and the result are all of the
 * same type and the throwing specialization of {@link BinaryOperator}.
 *
 * <p>This is a functional interface whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <T> the type of the operands and result of the operator
 * @param <E> the type of exception that may be thrown
 * @see ThrowingBiFunction
 * @see ThrowingUnaryOperator
 * @see BinaryOperator
 */
@FunctionalInterface
public interface ThrowingBinaryOperator<T, E extends Exception> extends ThrowingBiFunction<T, T, T, E> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @param <T>    the type of the operands and result of the operator
     * @return a safe operation upon two operands of the same type, producing a result of the same type as the operands
     */
    static <T> BinaryOperator<T> protect(final ThrowingBinaryOperator<T, Exception> lambda) {
        return (t, u) -> {
            try {
                return lambda.apply(t, u);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Returns a {@link ThrowingBinaryOperator} which returns the lesser of two elements according to the specified
     * {@code Comparator}.
     *
     * @param <T>        the type of the input arguments of the comparator
     * @param comparator a {@code Comparator} for comparing the two values
     * @return a {@code ThrowingBinaryOperator} which returns the lesser of its operands, according to the supplied
     *         {@code Comparator}
     * @throws NullPointerException if the argument is null
     */
    static <T, E extends Exception> ThrowingBinaryOperator<T, E> minBy(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
    }

    /**
     * Returns a {@link ThrowingBinaryOperator} which returns the greater of two elements according to the specified
     * {@code Comparator}.
     *
     * @param <T>        the type of the input arguments of the comparator
     * @param comparator a {@code Comparator} for comparing the two values
     * @return a {@code ThrowingBinaryOperator} which returns the greater of its operands, according to the supplied
     *         {@code Comparator}
     * @throws NullPointerException if the argument is null
     */
    static <T, E extends Exception> ThrowingBinaryOperator<T, E> maxBy(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
    }
}
