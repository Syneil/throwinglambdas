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
 * Represents a predicate (boolean-valued function) of two arguments. This is the two-arity specialization of
 * {@link ThrowingPredicate} and the throwing specialization of {@link BiPredicate}.
 *
 * <p>This is a functional interface whose functional method is {@link #test(Object, Object)}.
 *
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument to the predicate
 * @param <E> the type of exception that may be thrown
 * @see ThrowingPredicate
 * @see BiPredicate
 */
@FunctionalInterface
public interface ThrowingBiPredicate<T, U, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe predicate
     * @param <T>    the type of the first argument to the predicate
     * @param <U>    the type of the second argument to the predicate
     * @return a safe predicate of two arguments
     */
    static <T, U> BiPredicate<T, U> protect(final ThrowingBiPredicate<? super T, ? super U, Exception> lambda) {
        return (t, u) -> {
            try {
                return lambda.test(t, u);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
     * @throws E
     */
    boolean test(T t, U u) throws E;

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code false}, then the {@code other} predicate is not
     * evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed to the caller as the cause of a
     * {@link LambdaException}. If evaluation of this predicate throws an exception, the {@code other} predicate will
     * not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this predicate
     * @return a composed predicate that represents the short-circuiting logical AND of this predicate and the {@code
     *         other} predicate
     * @throws NullPointerException if other is null
     */
    default ThrowingBiPredicate<T, U, E> and(final ThrowingBiPredicate<? super T, ? super U, ? extends E> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) && other.test(t, u);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    default ThrowingBiPredicate<T, U, E> negate() {
        return (T t, U u) -> !test(t, u);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code true}, then the {@code other} predicate is not
     * evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed to the caller as the cause of a
     * {@link LambdaException}. If evaluation of this predicate throws an exception, the {@code other} predicate will
     * not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this predicate
     * @return a composed predicate that represents the short-circuiting logical OR of this predicate and the
     *         {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default ThrowingBiPredicate<T, U, E> or(final ThrowingBiPredicate<? super T, ? super U, ? extends E> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) || other.test(t, u);
    }
}
