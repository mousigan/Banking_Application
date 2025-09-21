package com.mousigan.entity;

import java.time.LocalDate;

public class Transaction {
    private LocalDate transactionDate;
    private String transactionUserId;
    private double amount;
    private String transactionType;
    private double initialBalance;
    private double finalbalance;
    private String transactionPerformedBy;

    public Transaction(LocalDate transactionDate, String transactionUserId,
                       double amount, String transactionType, double initialBalance, double finalbalance, String transactionPerformedBy) {
        this.transactionDate = transactionDate;
        this.transactionUserId = transactionUserId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.initialBalance = initialBalance;
        this.finalbalance = finalbalance;
        this.transactionPerformedBy = transactionPerformedBy;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionUserId() {
        return transactionUserId;
    }

    public void setTransactionUserId(String transactionUserId) {
        this.transactionUserId = transactionUserId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getFinalbalance() {
        return finalbalance;
    }

    public void setFinalbalance(double finalbalance) {
        this.finalbalance = finalbalance;
    }

    public String getTransactionPerformedBy() {
        return transactionPerformedBy;
    }

    public void setTransactionPerformedBy(String transactionPerformedBy) {
        this.transactionPerformedBy = transactionPerformedBy;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate=" + transactionDate +
                ", transactionUserId='" + transactionUserId + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", initialBalance=" + initialBalance +
                ", finalbalance=" + finalbalance +
                ", transactionPerformedBy='" + transactionPerformedBy + '\'' +
                '}';
    }
}
