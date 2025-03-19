package org.example.cryptotradingsim.service.impl;

import org.example.cryptotradingsim.model.dto.CryptoCurrencyDto;
import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.example.cryptotradingsim.model.entity.TransactionHistory;
import org.example.cryptotradingsim.model.entity.User;
import org.example.cryptotradingsim.model.entity.UserCrypto;
import org.example.cryptotradingsim.model.enums.TransactionType;
import org.example.cryptotradingsim.repository.CryptoRepository;
import org.example.cryptotradingsim.repository.UserCryptoRepository;
import org.example.cryptotradingsim.repository.UserRepository;
import org.example.cryptotradingsim.service.TransactionHistoryService;
import org.example.cryptotradingsim.service.TransactionService;
import org.example.cryptotradingsim.util.TransactionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final CryptoServiceImpl krakenService;

    private final UserRepository userRepository;

    private final CryptoRepository cryptoRepository;

    private final UserCryptoRepository userCryptoRepository;

    private final TransactionHistoryService transactionHistoryService;

    @Autowired
    public TransactionServiceImpl(CryptoServiceImpl krakenService, UserRepository userRepository, CryptoRepository cryptoRepository, UserCryptoRepository userCryptoRepository, TransactionHistoryService transactionHistoryService) {
        this.krakenService = krakenService;
        this.userRepository = userRepository;
        this.cryptoRepository = cryptoRepository;
        this.userCryptoRepository = userCryptoRepository;
        this.transactionHistoryService = transactionHistoryService;
    }

    @Override
    public String buyCryptoCurrency(CryptoCurrencyDto cryptoCurrencyDto) {
        CryptoCurrency cryptoCurrency = getRealTimeCryptoCurrency(cryptoCurrencyDto);

        boolean isValidPrice = validateCryptoPrice(cryptoCurrency.getAskPrice(), cryptoCurrencyDto.getPrice());

        User user = userRepository.getReferenceById(1L);
        BigDecimal balance = user.getBalance();
        BigDecimal price = cryptoCurrency.getAskPrice().multiply(cryptoCurrencyDto.getQuantity());
        if (isValidPrice) {
            if (balance.subtract(price).compareTo(BigDecimal.ZERO) < 0) {
                return TransactionMessages.FAILED_TRANSACTION_INSUFFICIENT_FUNDS;
            }
        } else {
            return TransactionMessages.FAILED_TRANSACTION_PRICE_CHANGE;
        }
        BigDecimal currentBalance = balance.subtract(price);
        user.setBalance(currentBalance);

        UserCrypto userCrypto = userCryptoRepository.findUserCryptoByUserAndCryptoCurrency(user, cryptoCurrency);
        CryptoCurrency cryptoCurrencyFromDB = cryptoRepository.findByPair(cryptoCurrencyDto.getPair());

        if (userCrypto != null) {
            userCrypto.setQuantity(userCrypto.getQuantity().add(cryptoCurrencyDto.getQuantity()));
        } else {
            userCrypto = new UserCrypto();
            userCrypto.setUser(user);
            userCrypto.setCryptoCurrency(cryptoCurrencyFromDB);
            userCrypto.setQuantity(cryptoCurrencyDto.getQuantity());
        }

        userCryptoRepository.save(userCrypto);
        userRepository.save(user);
        initTransactionHistory(cryptoCurrencyFromDB, price, TransactionType.BUY, cryptoCurrencyDto.getQuantity());
        return TransactionMessages.SUCCESSFUL_TRANSACTION;
    }

    @Override
    public String sellCryptoCurrency(CryptoCurrencyDto cryptoCurrencyDto) {
        CryptoCurrency cryptoCurrency = getRealTimeCryptoCurrency(cryptoCurrencyDto);
        boolean isValidPrice = validateCryptoPrice(cryptoCurrency.getBidPrice(), cryptoCurrencyDto.getPrice());

        User user = userRepository.getReferenceById(1L);
        UserCrypto userCrypto = userCryptoRepository.findUserCryptoByUserAndCryptoCurrency(user, cryptoCurrency);

        BigDecimal balance = user.getBalance();
        BigDecimal price = cryptoCurrency.getBidPrice().multiply(cryptoCurrencyDto.getQuantity());
        CryptoCurrency cryptoCurrencyFromDB = cryptoRepository.findByPair(cryptoCurrencyDto.getPair());

        if (userCrypto == null) {
            return TransactionMessages.FAILED_TRANSACTION_MISSING_CRYPTO;
        } else {
            if (isValidPrice) {
                if (userCrypto.getQuantity().compareTo(cryptoCurrencyDto.getQuantity()) < 0) {
                    return TransactionMessages.FAILED_TRANSACTION_NOT_ENOUGH_QUANTITY;
                }
            } else {
                return TransactionMessages.FAILED_TRANSACTION_PRICE_CHANGE;
            }
        }

        BigDecimal currentBalance = balance.add(price);
        user.setBalance(currentBalance);

        BigDecimal newQuantity = userCrypto.getQuantity().subtract(cryptoCurrencyDto.getQuantity());

        if (newQuantity.compareTo(BigDecimal.ZERO) == 0) {
            userCryptoRepository.delete(userCrypto);
        } else {
            userCrypto.setQuantity(newQuantity);
            userCryptoRepository.save(userCrypto);
        }

        userRepository.save(user);
        initTransactionHistory(cryptoCurrencyFromDB, price, TransactionType.SELL, cryptoCurrencyDto.getQuantity());

        return TransactionMessages.SUCCESSFUL_TRANSACTION;
    }


    @Override
    public boolean validateCryptoPrice(BigDecimal price1, BigDecimal price2) {
        if (price1.compareTo(price2) == 0) {
            return true;
        }
        BigDecimal difference = price1.subtract(price2).abs();
        BigDecimal threshold = price1.multiply(BigDecimal.valueOf(0.01));


        return difference.compareTo(threshold) <= 0;
    }


    @Override
    public void initTransactionHistory(CryptoCurrency cryptoCurrencyFromDB, BigDecimal price, TransactionType transactionType, BigDecimal quantity) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setCurrency(cryptoCurrencyFromDB);
        transactionHistory.setPrice(price);
        transactionHistory.setTransactionType(transactionType);
        transactionHistory.setQuantity(quantity);
        transactionHistoryService.saveTransactionHistory(transactionHistory);

    }


    @Override
    public CryptoCurrency getRealTimeCryptoCurrency(CryptoCurrencyDto cryptoCurrencyDto) {
        Map<String, CryptoCurrency> realTimeCryptoCurrencies = krakenService.getCryptoCurrencies();
        return realTimeCryptoCurrencies.get(cryptoCurrencyDto.getPair());
    }
}
