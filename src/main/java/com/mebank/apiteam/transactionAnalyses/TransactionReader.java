package com.mebank.apiteam.transactionAnalyses;

import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.opencsv.CSVReaderHeaderAware;

import ch.qos.logback.classic.Logger;

public class TransactionReader {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger(TransactionReader.class);

	public static List<Transaction> readTransactions(Reader reader) throws Exception {
		List<Transaction> list = new ArrayList<>();
		CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(reader);
		try {

			String[] tokens;
			while ((tokens = csvReader.readNext()) != null) {
				// transactionId, fromAccountId, toAccountId, createdAt, amount,
				// transactionType, relatedTransaction
				String transactionId = tokens[0].trim();
				String fromAccountId = tokens[1].trim();
				String toAccountId = tokens[2].trim();
				LocalDateTime createdAt = LocalDateTime.parse(tokens[3].trim(), formatter);
				BigDecimal amount = new BigDecimal(tokens[4].trim());
				TransactionType transactionType = TransactionType.getByName(tokens[5].trim()).get();
				String relatedTransaction = "";
				if (tokens.length > 6) {
					relatedTransaction = tokens[6].trim();
				}
				list.add(new Transaction(transactionId, fromAccountId, toAccountId, createdAt, amount, transactionType,
						relatedTransaction));
			}
			logger.info("{} transactions found.", list.size());
		} finally {
			reader.close();
			csvReader.close();
		}
		return list;
	}
}
