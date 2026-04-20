package com.rewards.rewards_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.rewards_api.dto.RewardResponse;
import com.rewards.rewards_api.service.RewardsService;

/**
 * REST controller that exposes endpoints for retrieving customer rewards.
 *
 * <p>This controller is part of the web layer in a layered architecture (Controller -> Service -> Repository).
 * Its primary responsibility is to handle HTTP requests related to rewards and delegate business
 * logic to the {@link RewardsService}. The controller performs no business calculations itself and
 * only converts incoming HTTP requests into service calls and returns the resulting DTOs.
 *
 * <p>Typical responsibilities:
 * <ul>
 *   <li>Expose REST endpoints for rewards-related operations.</li>
 *   <li>Delegate to the service layer for business processing.</li>
 *   <li>Return DTOs suitable for serialization to JSON.</li>
 * </ul>
 *
 * <p>This class is designed to be thin: all business rules and validations belong to the service
 * and repository layers. The controller is intentionally free of complex logic so it can be
 * exercised easily by integration tests and framework-level concerns (e.g. request mapping, filters).
 *
 * @author Rewards
 * @since 0.0.1
 */
@RestController
@RequestMapping("/rewards")
public class RewardsController {

	/** Service component that encapsulates rewards business logic. Injected via constructor. */
	private final RewardsService rewardsService;

	/**
	 * Constructs a new {@code RewardsController} with the required service dependency.
	 *
	 * @param rewardsService the service that provides rewards calculation and retrieval
	 * @since 0.0.1
	 */
	public RewardsController(RewardsService rewardsService) {
		this.rewardsService = rewardsService;
	}

	/**
	 * GET endpoint to return rewards aggregated per customer.
	 *
	 * <p>This endpoint delegates to {@link RewardsService#getAllRewards()} and returns the list
	 * of {@link RewardResponse} DTOs. The service performs all aggregation of transactions into
	 * 0-30, 31-60 and 61-90 day buckets and computes total points per customer. The controller
	 * simply returns the DTOs produced by the service for HTTP serialization.
	 *
	 * @return a list of {@link RewardResponse} representing per-customer reward summaries. The
	 *         returned list will contain one entry per customer with their aggregated points.
	 * @throws com.rewards.rewards_api.exception.TransactionNotFoundException if no transactions
	 *         exist in the underlying repository; this is propagated from the service layer and
	 *         results in a 404 when handled by the application's global exception handler.
	 * @since 0.0.1
	 */
	@GetMapping
	public List<RewardResponse> getAllRewards() {
		return rewardsService.getAllRewards();
	}
}