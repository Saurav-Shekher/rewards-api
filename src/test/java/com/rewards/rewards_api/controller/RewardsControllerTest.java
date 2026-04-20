package com.rewards.rewards_api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rewards.rewards_api.dto.RewardResponse;
import com.rewards.rewards_api.service.RewardsService;

@ExtendWith(MockitoExtension.class)
class RewardsControllerTest {

	@Mock
	private RewardsService rewardsService;

	private RewardsController controller;

	@BeforeEach
	void setup() {
		controller = new RewardsController(rewardsService);
	}

	@Test
	void testGetAllRewards_DelegatesToServiceAndReturnsList() {
		RewardResponse r1 = new RewardResponse("C1", 10, 20, 30);
		RewardResponse r2 = new RewardResponse("C2", 0, 5, 0);
		List<RewardResponse> expected = Arrays.asList(r1, r2);

		when(rewardsService.getAllRewards()).thenReturn(expected);

		List<RewardResponse> actual = controller.getAllRewards();

		assertNotNull(actual);
		assertEquals(2, actual.size());
		assertEquals("C1", actual.get(0).getCustomerId());
		assertEquals(60, actual.get(0).getTotalPoints());
		assertEquals("C2", actual.get(1).getCustomerId());

		verify(rewardsService).getAllRewards();
	}

	@Test
	void testGetAllRewards_EmptyList() {
		when(rewardsService.getAllRewards()).thenReturn(Arrays.asList());

		List<RewardResponse> actual = controller.getAllRewards();

		assertNotNull(actual);
		assertTrue(actual.isEmpty());

		verify(rewardsService).getAllRewards();
	}
}
