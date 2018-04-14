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
 * Represents a predicate (boolean-valued function) of one {@code double}-valued argument. This is the
 * {@code double}-consuming primitive type specialization of {@link ThrowingPredicate} and the throwing specialization
 * of {@link DoublePredicate}.
 *
 * <p>This is a functional interface whose functional method is {@link #test(double)}.
 *
 * @see ThrowingPredicate
 * @see DoublePredicate
 */
@FunctionalInterface
public interface ThrowingDoublePredicate<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe predicate
     * @return a safe predicate of a double
     */
    static DoublePredicate protect(final ThrowingDoublePredicate<Exception> lambda) {
        return value -> {
            try {
                return lambda.test(value);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E
     */
    boolean test(double value) throws E;

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code false}, then the {@code other} predicate is not
     * evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed to the caller. If evaluation of this
     * predicate throws an exception, the {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this predicate
     * @return a composed predicate that represents the short-circuiting logical AND of this predicate and the
     *         {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default ThrowingDoublePredicate<E> and(final ThrowingDoublePredicate<? extends E> other) {
        Objects.requireNonNull(other);
        return (value) -> test(value) && other.test(value);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    default ThrowingDoublePredicate<E> negate() {
        return value -> !test(value);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code true}, then the {@code other} predicate is not
     * evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed to the caller. If evaluation of this
     * predicate throws an exception, the {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this predicate
     * @return a composed predicate that represents the short-circuiting logical OR of this predicate and the
     *         {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default ThrowingDoublePredicate<E> or(final ThrowingDoublePredicate<? extends E> other) {
        Objects.requireNonNull(other);
        return value -> test(value) || other.test(value);
    }
}
