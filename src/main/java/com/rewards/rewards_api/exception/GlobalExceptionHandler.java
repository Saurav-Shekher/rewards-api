package com.rewards.rewards_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Global exception handler for REST controllers.
	 *
	 * <p>
	 * This component centralizes translation of exceptions to HTTP responses. It
	 * allows controllers and services to throw domain exceptions and rely on a
	 * consistent mapping to status codes and user-visible messages.
	 *
	 * @since 0.0.1
	 */

	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<String> handleTransactionNotFound(TransactionNotFoundException ex) {
		// Return the exception message with HTTP 404 Not Found.
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Catch-all exception handler.
	 *
	 * <p>
	 * Maps unexpected exceptions to a generic 500 response. In production this
	 * method should be augmented to log the exception and avoid leaking internal
	 * details to clients.
	 *
	 * @param ex the uncaught exception
	 * @return a ResponseEntity with a generic error message and HTTP 500 status
	 * @since 0.0.1
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}