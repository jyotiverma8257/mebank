package com.mebank.apiteam.transactionAnalyses;

import java.util.Optional;

public enum TransactionType {

	PAYMENT(), REVERSAL();

	public static Optional<TransactionType> getByName(String type) {
		for (TransactionType transactionType : TransactionType.values()) {
			if (transactionType.name().equalsIgnoreCase(type)) {
				return Optional.of(transactionType);
			}
		}
		return Optional.empty();
	}

}
