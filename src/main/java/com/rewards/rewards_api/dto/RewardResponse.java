package com.rewards.rewards_api.dto;

/**
 * Data Transfer Object that represents aggregated reward points for a customer.
 *
 * <p>This DTO is returned by the controller and serialized to JSON for API clients.
 * It contains the points accumulated in three time windows (0-30, 31-60 and 61-90 days)
 * and a computed total. The DTO is intentionally simple and provides read-only accessors
 * for serialization and test assertions.
 *
 * @author Rewards
 * @since 0.0.1
 */
public class RewardResponse {

    private String customerId;
    private long within1month;
    private long within1to2months;
    private long within2to3months;
    private long totalPoints;

    /**
     * Create a new reward response for a customer.
     *
     * @param customerId customer identifier
     * @param m1 points accumulated within 0-30 days
     * @param m2 points accumulated within 31-60 days
     * @param m3 points accumulated within 61-90 days
     * @since 0.0.1
     */
    public RewardResponse(String customerId, long m1, long m2, long m3) {
        this.customerId = customerId;
        this.within1month = m1;
        this.within1to2months = m2;
        this.within2to3months = m3;
        this.totalPoints = m1 + m2 + m3;
    }

    /** Returns the customer identifier. */
    public String getCustomerId() {
        return customerId;
    }

    /** Returns points accumulated within 0-30 days. */
    public long getWithin1month() {
        return within1month;
    }

    /** Returns points accumulated within 31-60 days. */
    public long getWithin1to2months() {
        return within1to2months;
    }

    /** Returns points accumulated within 61-90 days. */
    public long getWithin2to3months() {
        return within2to3months;
    }

    /** Returns the total aggregated points (m1 + m2 + m3). */
    public long getTotalPoints() {
        return totalPoints;
    }
}