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
}