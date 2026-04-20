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

@Service
public class RewardsService {

	private final TransactionRepository repository;

	public RewardsService(TransactionRepository repository) {
		this.repository = repository;
	}

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