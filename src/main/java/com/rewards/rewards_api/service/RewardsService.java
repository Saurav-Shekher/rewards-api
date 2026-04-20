package com.rewards.rewards_api.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rewards.rewards_api.dto.RewardResponse;
import com.rewards.rewards_api.entity.Transaction;
import com.rewards.rewards_api.exception.TransactionNotFoundException;
import com.rewards.rewards_api.repository.TransactionRepository;

/**
 * Service that implements rewards calculation and aggregation logic.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Load transactions from the {@link com.rewards.rewards_api.repository.TransactionRepository}.</li>
 *   <li>Compute reward points according to business rules and aggregate them per customer
 *       into time buckets (0-30, 31-60 and 61-90 days).</li>
 *   <li>Return {@link com.rewards.rewards_api.dto.RewardResponse} DTOs for consumption by the web layer.</li>
 * </ul>
 *
 * <p>The service encapsulates business rules and keeps the controller thin. It throws
 * domain-specific exceptions (for example {@link com.rewards.rewards_api.exception.TransactionNotFoundException})
 * which are translated to HTTP responses by the global exception handler.
 *
 * @author Rewards
 * @since 0.0.1
 */
@Service
public class RewardsService {

	private final TransactionRepository repository;

	public RewardsService(TransactionRepository repository) {
		this.repository = repository;
	}

					/**
					 * Retrieve aggregated reward points for all customers.
					 *
					 * <p>This method queries the {@link TransactionRepository} for all transactions and
					 * aggregates points per customer into three time buckets: 0-30 days, 31-60 days and
					 * 61-90 days (relative to {@link java.time.LocalDate#now()}). Points are computed using
					 * {@link #calculatePoints(double)} and summed per bucket. Transactions older than 90 days
					 * are ignored.
					 *
					 * <p>If no transactions are available this method throws {@link com.rewards.rewards_api.exception.TransactionNotFoundException}.
					 *
					 * @return a list of {@link RewardResponse} with per-customer aggregated points
					 * @throws com.rewards.rewards_api.exception.TransactionNotFoundException when repository contains no transactions
					 * @since 0.0.1
					 */
        public List<RewardResponse> getAllRewards() {

		List<Transaction> transactions = repository.findAll();

		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transactions found");
		}

		LocalDate now = LocalDate.now();

		Map<String, List<Transaction>> grouped = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getCustomerId));

		List<RewardResponse> responses = new ArrayList<>();

		for (String customerId : grouped.keySet()) {

			long m1 = 0;
			long m2 = 0;
			long m3 = 0;

			for (Transaction t : grouped.get(customerId)) {

				long points = calculatePoints(t.getTransactionAmount());

				long days = ChronoUnit.DAYS.between(t.getTransactionDate(), now);

				if (days <= 30)
					m1 += points;
				else if (days <= 60)
					m2 += points;
				else if (days <= 90)
					m3 += points;
			}

			responses.add(new RewardResponse(customerId, m1, m2, m3));
		}

		return responses;
	}

	/**
	 * Calculate reward points for a single transaction amount using the business rules:
	 * <ul>
	 *   <li>No points for amounts <= 50.</li>
	 *   <li>1 point per dollar for amount between 50 and 100 (exclusive of 50).</li>
	 *   <li>2 points per dollar for amount above 100 plus the fixed 50 points for the 50-100 range.
	 * </ul>
	 *
	 * <p>Examples:
	 * <ul>
	 *   <li>amount = 50 -> 0 points</li>
	 *   <li>amount = 75 -> 25 points</li>
	 *   <li>amount = 120 -> (20 * 2) + 50 = 90 points</li>
	 * </ul>
	 *
	 * @param amount the transaction amount in dollars; must be non-negative
	 * @return the computed points as a long (fractional points are truncated)
	 * @throws IllegalArgumentException if {@code amount} is negative
	 * @since 0.0.1
	 */
	private long calculatePoints(double amount) {

		if (amount < 0)
			throw new IllegalArgumentException("Transaction amount cannot be negative");

		if (amount > 100)
			return (long) ((amount - 100) * 2 + 50);

		if (amount > 50)
			return (long) (amount - 50);

		return 0;
	}
}