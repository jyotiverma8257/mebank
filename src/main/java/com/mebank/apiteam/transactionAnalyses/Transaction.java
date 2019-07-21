package com.mebank.apiteam.transactionAnalyses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

	private String transactionId;

	private String fromAccountId;

	private String toAccountId;

	private LocalDateTime createdAt;

	private BigDecimal amount;

	private TransactionType transactionType;

	private String relatedTransaction;

	public Transaction(String transactionId, String fromAccountId, String toAccountId, LocalDateTime createdAt,
			BigDecimal amount, TransactionType transactionType, String relatedTransaction) {
		super();
		this.transactionId = transactionId;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.createdAt = createdAt;
		this.amount = amount;
		this.transactionType = transactionType;
		this.relatedTransaction = relatedTransaction;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getRelatedTransaction() {
		return relatedTransaction;
	}

	public void setRelatedTransaction(String relatedTransaction) {
		this.relatedTransaction = relatedTransaction;
	}

}
