package com.mebank.apiteam.transactionAnalyses;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class TransactionApp {

	private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(TransactionApp.class);
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private final Map<String, List<Transaction>> accountTransactionMap = new HashMap<>();

	public static void main(String[] args) {

		if (args.length != 4) {
			logger.error("Invalid arguments");
			logger.error("Usage: java -jar <jar_name> <csv_path> <accountId> <\"fromDate\"> <\"toDate\">");
			System.exit(1);
		}
		TransactionApp transactionApp = new TransactionApp();
		try {
			Reader reader = Files.newBufferedReader(Paths.get(args[0]));
			transactionApp.init(reader);
			Input searchCriteriaInput = transactionApp.getSearchInput(args);
			List<Transaction> filteredTransactions = transactionApp.getFilteredTransactions(searchCriteriaInput);
			BigDecimal amount = transactionApp.aggregateTransaction(searchCriteriaInput.getAccountId(),
					filteredTransactions);
			logger.info("Amount = " + amount);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("try again...");
			System.exit(1);
		}

	}

	public List<Transaction> getFilteredTransactions(Input searchCriteriaInput) {
		if (accountTransactionMap.containsKey(searchCriteriaInput.getAccountId())) {
			List<Transaction> windowTransactionsList = accountTransactionMap.get(searchCriteriaInput.getAccountId())
					.stream()
					.filter(t -> t.getCreatedAt().isAfter(searchCriteriaInput.getFromDate())
							&& t.getCreatedAt().isBefore(searchCriteriaInput.getToDate()))
					.filter(t -> t.getTransactionType().equals(TransactionType.PAYMENT)).collect(Collectors.toList());
			// Filter Reversal
			final List<Transaction> reversalTransactionList = accountTransactionMap
					.get(searchCriteriaInput.getAccountId()).stream()
					.filter(txn -> txn.getTransactionType().equals(TransactionType.REVERSAL))
					.collect(Collectors.toList());

			List<Transaction> filteredTransactionList = windowTransactionsList.stream()
					.filter(txn -> !reversalTransactionList.stream().map(rt -> rt.getRelatedTransaction())
							.collect(Collectors.toList()).contains(txn.getTransactionId()))
					.collect(Collectors.toList());

			return filteredTransactionList;
		}
		return new ArrayList<>();
	}

	private Input getSearchInput(String[] args) {
		logger.info("Input parameters:");
		String accountId = args[1];
		String fromDateStr = args[2];
		String toDateStr = args[3];

		logger.info("AccountId: " + accountId);
		logger.info("From Date: " + fromDateStr);
		logger.info("To Date: " + toDateStr);
		try {
			LocalDateTime fromDate = LocalDateTime.parse(fromDateStr, formatter);
			LocalDateTime toDate = LocalDateTime.parse(toDateStr, formatter);
			return new Input(accountId, fromDate, toDate);
		} catch (DateTimeParseException e) {
			logger.error("Invalid date : " + e.getMessage());
			throw e;
		}

	}

	public void init(Reader reader) {
		try {
			List<Transaction> allTransactions = TransactionReader.readTransactions(reader);
			for (Transaction t : allTransactions) {
				if (accountTransactionMap.containsKey(t.getFromAccountId())) {
					accountTransactionMap.get(t.getFromAccountId()).add(t);
				} else {
					accountTransactionMap.put(t.getFromAccountId(), new ArrayList<Transaction>() {
						{
							add(t);
						}
					});
				}

				if (accountTransactionMap.containsKey(t.getToAccountId())) {
					accountTransactionMap.get(t.getToAccountId()).add(t);
				} else {
					accountTransactionMap.put(t.getToAccountId(), new ArrayList<Transaction>() {
						{
							add(t);
						}
					});
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public BigDecimal aggregateTransaction(String accountId, List<Transaction> filteredTransactionList) {
		BigDecimal amount = BigDecimal.ZERO;
		for (Transaction t : filteredTransactionList) {
			if (t.getFromAccountId().equals(accountId)) {
				amount = amount.subtract(t.getAmount());
			} else if (t.getToAccountId().equals(accountId)) {
				amount = amount.add(t.getAmount());
			}
		}
		return amount;
	}

}
