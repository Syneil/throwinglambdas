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
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * <p>This is a functional interface whose functional method is {@link #test(Object)}.
 *
 * @param <T> the type of the input to the predicate
 * @param <E> the type of exception that may be thrown
 * @see Predicate
 */
@FunctionalInterface
public interface ThrowingPredicate<T, E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe predicate
     * @param <T>    the type of the input to the predicate
     * @return a safe predicate of one argument
     */
    static <T> Predicate<T> protect(final ThrowingPredicate<? super T, Exception> lambda) {
        return t -> {
            try {
                return lambda.test(t);
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * Returns a predicate that tests if two arguments are equal according to {@link Objects#equals(Object, Object)}.
     *
     * @param <T>       the type of arguments to the predicate
     * @param <E>       the type of exception that may be thrown
     * @param targetRef the object reference with which to compare for equality, which may be {@code null}
     * @return a predicate that tests if two arguments are equal according to {@link Objects#equals(Object, Object)}
     */
    static <T, E extends Exception> ThrowingPredicate<T, E> isEqual(final Object targetRef) {
        return null == targetRef ? Objects::isNull : targetRef::equals;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws E
     */
    boolean test(T t) throws E;

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
    default ThrowingPredicate<T, E> and(final ThrowingPredicate<? super T, ? extends E> other) {
        Objects.requireNonNull(other);
        return t -> test(t) && other.test(t);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    default ThrowingPredicate<T, E> negate() {
        return t -> !test(t);
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
    default ThrowingPredicate<T, E> or(final ThrowingPredicate<? super T, ? extends E> other) {
        Objects.requireNonNull(other);
        return t -> test(t) || other.test(t);
    }
}
