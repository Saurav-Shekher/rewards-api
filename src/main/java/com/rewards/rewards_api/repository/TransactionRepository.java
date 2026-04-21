package com.rewards.rewards_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rewards.rewards_api.entity.Transaction;

/**
 * Repository abstraction for accessing {@link Transaction} entities.
 *
 * <p>
 * This interface extends Spring Data JPA's {@code JpaRepository} to provide
 * standard CRUD and query operations for transactions. Custom queries (if
 * required) can be added here as method signatures and implemented by Spring
 * Data at runtime.
 *
 * @since 0.0.1
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  /**
 * Retrieves all {@link Transaction} records whose transaction date falls
 * between the specified start and end dates (inclusive).
 *
 * <p>
 * This method is typically used to limit queries to a specific time window,
 * such as fetching transactions from the last three months for reward
 * calculation. Filtering is performed at the database level to improve
 * performance and reduce memory usage for large datasets.
 * </p>
 *
 * <p>
 * The underlying query is automatically derived by Spring Data JPA based on
 * the method name.
 * </p>
 *
 * @param startDate the start date of the range (inclusive); must not be {@code null}
 * @param endDate   the end date of the range (inclusive); must not be {@code null}
 * @return a list of {@link Transaction} entities whose transaction date is
 *         between {@code startDate} and {@code endDate}; returns an empty list
 *         if no matching transactions are found
 */
  List<Transaction> findByTransactionDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );
}
