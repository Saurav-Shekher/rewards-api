package com.rewards.rewards_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rewards.rewards_api.dto.RewardResponse;
import com.rewards.rewards_api.entity.Transaction;
import com.rewards.rewards_api.exception.TransactionNotFoundException;
import com.rewards.rewards_api.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

	@Mock
	private TransactionRepository repository;

	private RewardsService service;

	@BeforeEach
	void setup() {
		service = new RewardsService(repository);
	}

	@Test
	void testGetAllRewards_SuccessfulAggregation() {
		LocalDate now = LocalDate.now();

		List<Transaction> transactions = Arrays.asList(

				new Transaction(1L, "CUST_A", now.minusDays(10), 120.75),
				new Transaction(2L, "CUST_A", now.minusDays(5), 200.0),
				new Transaction(3L, "CUST_A", now.minusDays(45), 70.0),
				new Transaction(4L, "CUST_A", now.minusDays(100), 150.0),

				// Customer B: boundary amounts
				new Transaction(5L, "CUST_B", now.minusDays(20), 50.0),
				new Transaction(6L, "CUST_B", now.minusDays(35), 100.0));

		when(repository.findAll()).thenReturn(transactions);

		List<RewardResponse> responses = service.getAllRewards();

		assertEquals(2, responses.size(), "Should return two customers");

		RewardResponse a = responses.stream().filter(r -> "CUST_A".equals(r.getCustomerId())).findFirst().orElseThrow();
		// m1: 91 + 250 = 341
		assertEquals(341, a.getWithin1month());
		// m2: 20
		assertEquals(20, a.getWithin1to2months());
		// m3: none
		assertEquals(0, a.getWithin2to3months());
		assertEquals(361, a.getTotalPoints());

		RewardResponse b = responses.stream().filter(r -> "CUST_B".equals(r.getCustomerId())).findFirst().orElseThrow();
		// 50 -> 0 in m1, 100 -> 50 in m2
		assertEquals(0, b.getWithin1month());
		assertEquals(50, b.getWithin1to2months());
		assertEquals(0, b.getWithin2to3months());
		assertEquals(50, b.getTotalPoints());
	}

	@Test
	void testGetAllRewards_Empty_ThrowsTransactionNotFoundException() {
		when(repository.findAll()).thenReturn(Collections.emptyList());

		assertThrows(TransactionNotFoundException.class, () -> service.getAllRewards());
	}

	@Test
	void testGetAllRewards_NegativeAmount_ThrowsIllegalArgumentException() {
		LocalDate now = LocalDate.now();
		List<Transaction> transactions = Collections
				.singletonList(new Transaction(1L, "CUST_NEG", now.minusDays(1), -10.0));

		when(repository.findAll()).thenReturn(transactions);

		assertThrows(IllegalArgumentException.class, () -> service.getAllRewards());
	}
}
