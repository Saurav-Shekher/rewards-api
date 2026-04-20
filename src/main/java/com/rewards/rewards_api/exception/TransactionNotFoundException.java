package com.rewards.rewards_api.exception;

public class TransactionNotFoundException extends RuntimeException {

    /**
     * Exception thrown when no transactions are found in the repository for a requested operation.
     *
     * <p>This is an unchecked exception intentionally used to indicate a missing-domain-data
     * condition which is translated to HTTP 404 by {@link GlobalExceptionHandler}.
     *
     * @param message diagnostic message describing the missing resource
     * @since 0.0.1
     */
    public TransactionNotFoundException(String message) {
        super(message);
    }
}