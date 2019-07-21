package com.mebank.apiteam.transactionAnalyses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionAppTest {

	private TransactionApp transactionApp;
	Reader reader;

	String input = "transactionId, fromAccountId, toAccountId, createdAt, amount, transactionType, relatedTransaction\r\n"
			+ "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT,\r\n"
			+ "TX10002, ACC334455, ACC998877, 20/10/2018 17:33:43, 10.50, PAYMENT,\r\n"
			+ "TX10003, ACC998877, ACC778899, 20/10/2018 18:00:00, 5.00, PAYMENT,\r\n"
			+ "TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL, TX10002\r\n"
			+ "TX10005, ACC334455, ACC778899, 21/10/2018 09:30:00, 7.25, PAYMENT,";

	@BeforeEach
	public void setUp() {
		transactionApp = new TransactionApp();
		Reader inputString = new StringReader(input);
		reader = new BufferedReader(inputString);
		transactionApp.init(reader);
	}

	@Test
	public void testGetFilteredTransactions() {
		Input input = new Input("ACC334455", LocalDateTime.of(2011, 11, 1, 02, 02), LocalDateTime.now());
		List<Transaction> transactionList = transactionApp.getFilteredTransactions(input);
		assertEquals(2, transactionList.size());

	}

	@Test
	public void testGetFilteredTransactionNoMatchingRecord() {
		Input input = new Input("ACC99999", LocalDateTime.of(2011, 11, 1, 02, 02), LocalDateTime.now());
		List<Transaction> transactionList = transactionApp.getFilteredTransactions(input);
		assertEquals(0, transactionList.size());
	}

	@Test
	public void testAggregateTransaction() {
		Input input = new Input("ACC334455", LocalDateTime.of(2011, 11, 1, 02, 02), LocalDateTime.now());
		List<Transaction> transactionList = transactionApp.getFilteredTransactions(input);
		BigDecimal amount = transactionApp.aggregateTransaction("ACC334455", transactionList);
		assertEquals(amount, new BigDecimal(-32.25));
	}

}
