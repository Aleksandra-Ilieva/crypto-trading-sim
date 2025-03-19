package org.example.cryptotradingsim.service;

import org.example.cryptotradingsim.model.dto.TransactionHistoryDto;
import org.example.cryptotradingsim.model.entity.TransactionHistory;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionHistoryService {


    List<TransactionHistoryDto> getAllTransactionHistory();

    void saveTransactionHistory(TransactionHistory transactionHistory);

    String getCurrentBalanceState();

    String getFormatedBalanceString(BigDecimal initialBalance);
}
