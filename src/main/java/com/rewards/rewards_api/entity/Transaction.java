package com.rewards.rewards_api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * JPA entity representing a transaction record.
 *
 * <p>Maps to the {@code t_transaction_details} table and is used by the repository layer to
 * persist and load transaction data. The entity is intentionally simple and does not contain
 * business logic; business rules are implemented in the service layer where transactions are
 * interpreted and aggregated into reward points.
 *
 * <p>Fields correspond directly to table columns and are accessed via standard JavaBean
 * accessors. Avoid placing business logic here to keep the entity focused on persistence.
 *
 * @author Rewards
 * @since 0.0.1
 */
@Entity
@Table(name = "t_transaction_details")
public class Transaction {

    /** Primary key for the transaction. */
    @Id
    private Long transactionId;

    /** Identifier for the customer who made the transaction. */
    private String customerId;

    /** Date when the transaction was performed. */
    private LocalDate transactionDate;

    /** Monetary amount for the transaction. */
    private Double transactionAmount;

    /**
     * Default constructor required by JPA.
     */
    public Transaction() {
    }

    /**
     * All-args constructor used in tests and programmatic creation.
     *
     * @param transactionId primary key
     * @param customerId customer identifier
     * @param transactionDate date of the transaction
     * @param transactionAmount amount of the transaction
     */
    public Transaction(Long transactionId, String customerId, LocalDate transactionDate, Double transactionAmount) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}