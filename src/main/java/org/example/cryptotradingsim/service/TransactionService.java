package org.example.cryptotradingsim.service;

import org.example.cryptotradingsim.model.dto.CryptoCurrencyDto;
import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.example.cryptotradingsim.model.enums.TransactionType;

import java.math.BigDecimal;

public interface TransactionService {

    String buyCryptoCurrency(CryptoCurrencyDto cryptoCurrencyDto);

    boolean validateCryptoPrice(BigDecimal price1, BigDecimal price2);

    String sellCryptoCurrency(CryptoCurrencyDto cryptoCurrencyDto);

    void initTransactionHistory(CryptoCurrency cryptoCurrencyFromDB, BigDecimal price, TransactionType transactionType, BigDecimal quantity);

    CryptoCurrency getRealTimeCryptoCurrency(CryptoCurrencyDto cryptoCurrencyDto);

}
