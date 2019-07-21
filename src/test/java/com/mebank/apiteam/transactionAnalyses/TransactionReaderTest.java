package com.mebank.apiteam.transactionAnalyses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionReaderTest {
	Reader reader;

	String input = "transactionId, fromAccountId, toAccountId, createdAt, amount, transactionType, relatedTransaction\r\n"
			+ "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT,\r\n"
			+ "TX10002, ACC334455, ACC998877, 20/10/2018 17:33:43, 10.50, PAYMENT,\r\n"
			+ "TX10003, ACC998877, ACC778899, 20/10/2018 18:00:00, 5.00, PAYMENT,\r\n"
			+ "TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL, TX10002\r\n"
			+ "TX10005, ACC334455, ACC778899, 21/10/2018 09:30:00, 7.25, PAYMENT,";

	@BeforeEach
	public void setUp() {
		Reader inputString = new StringReader(input);
		reader = new BufferedReader(inputString);
	}

	@Test
	public void testGetFilteredTransactions() {
		try {
			assertEquals(5, TransactionReader.readTransactions(reader).size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
