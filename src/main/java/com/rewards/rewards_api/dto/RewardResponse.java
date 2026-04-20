package com.rewards.rewards_api.dto;

import lombok.Getter;

@Getter
public class RewardResponse {

    private String customerId;
    private long within1month;
    private long within1to2months;
    private long within2to3months;
    private long totalPoints;

    public RewardResponse(String customerId, long m1, long m2, long m3) {
        this.customerId = customerId;
        this.within1month = m1;
        this.within1to2months = m2;
        this.within2to3months = m3;
        this.totalPoints = m1 + m2 + m3;
    }
}