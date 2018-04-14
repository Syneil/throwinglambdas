/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.concurrent.*;

/**
 * The {@code ThrowingRunnable} interface should be implemented by any class whose instances are intended to be
 * executed by a thread. The class must define a method of no arguments called {@code run}.
 * <p>
 * This interface is designed to provide a common protocol for objects that wish to execute code while they are active.
 * For example, {@code Runnable} is implemented by class {@code Thread}. Being active simply means that a thread has
 * been started and has not yet been stopped.
 * <p>
 * In addition, {@code Runnable} provides the means for a class to be active while not subclassing {@code Thread}. A
 * class that implements {@code Runnable} can run without subclassing {@code Thread} by instantiating a {@code Thread}
 * instance and passing itself in as the target. In most cases, the {@code Runnable} interface should be used if you
 * are only planning to override the {@code run()} method and no other {@code Thread} methods. This is important
 * because classes should not be subclassed unless the programmer intends on modifying or enhancing the fundamental
 * behavior of the class.
 *
 * @see Thread
 * @see Callable
 * @see Runnable
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {
    /**
     * Returns a safe version of lambda that catches the checked exception and wraps it as the cause of a runtime
     * {@link LambdaException}.
     *
     * @param lambda the unsafe operation
     * @return a safe operation that accepts no parameters and returns no result; can be used with {@link Thread#run()}
     */
    static Runnable protect(final ThrowingRunnable<Exception> lambda) {
        return () -> {
            try {
                lambda.run();
            } catch (final Exception ex) {
                throw new LambdaException(ex);
            }
        };
    }

    /**
     * When an object implementing interface {@code Runnable} is used to create a thread, starting the thread
     * causes the object's {@code run} method to be called in that separately executing thread.
     * <p>
     * The general contract of the method {@code run} is that it may take any action whatsoever.
     *
     * @throws E
     * @see Runnable
     */
    void run() throws E;
}
