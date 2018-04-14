package com.syneil.throwinglambdas;

/**
 * Thrown from within functional interfaces to wrap checked exceptions that would otherwise violate the standard
 * {@code java.util.function} interfaces.
 */
public class LambdaException extends RuntimeException {
    /**
     * Constructor with a cause
     * @param cause the underlying cause of the exception
     */
    public LambdaException(final Exception cause) {
        super(cause);
    }
}
