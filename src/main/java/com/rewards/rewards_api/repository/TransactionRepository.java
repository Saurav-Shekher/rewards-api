package com.rewards.rewards_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rewards.rewards_api.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}