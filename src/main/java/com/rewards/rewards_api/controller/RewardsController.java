package com.rewards.rewards_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.rewards_api.dto.RewardResponse;
import com.rewards.rewards_api.service.RewardsService;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

	private final RewardsService rewardsService;

	public RewardsController(RewardsService rewardsService) {
		this.rewardsService = rewardsService;
	}

	@GetMapping
	public List<RewardResponse> getAllRewards() {
		return rewardsService.getAllRewards();
	}
}