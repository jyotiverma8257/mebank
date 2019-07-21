package com.mebank.apiteam.transactionAnalyses;

import java.time.LocalDateTime;

public class Input {
	String accountId;
	LocalDateTime fromDate;
	LocalDateTime toDate;

	public Input(String accountId, LocalDateTime fromDate, LocalDateTime toDate) {
		this.accountId = accountId;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}
}