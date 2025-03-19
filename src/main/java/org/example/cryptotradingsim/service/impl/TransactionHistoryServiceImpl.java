package org.example.cryptotradingsim.service.impl;

import org.example.cryptotradingsim.client.KrakenWebSocketClient;
import org.example.cryptotradingsim.model.dto.TransactionHistoryDto;
import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.example.cryptotradingsim.model.entity.TransactionHistory;
import org.example.cryptotradingsim.model.entity.User;
import org.example.cryptotradingsim.model.entity.UserCrypto;
import org.example.cryptotradingsim.repository.TransactionHistoryRepository;
import org.example.cryptotradingsim.repository.UserCryptoRepository;
import org.example.cryptotradingsim.repository.UserRepository;
import org.example.cryptotradingsim.service.TransactionHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository historyRepository;

    private final ModelMapper modelMapper;

    private final UserCryptoRepository userCryptoRepository;

    private final KrakenWebSocketClient krakenWebSocketClient;

    private final UserRepository userRepository;

    public TransactionHistoryServiceImpl(TransactionHistoryRepository historyRepository, ModelMapper modelMapper,
                                         UserCryptoRepository userCryptoRepository, KrakenWebSocketClient krakenWebSocketClient, UserRepository userRepository) {
        this.krakenWebSocketClient = krakenWebSocketClient;
        this.historyRepository = historyRepository;
        this.modelMapper = modelMapper;
        this.userCryptoRepository = userCryptoRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<TransactionHistoryDto> getAllTransactionHistory() {
        DecimalFormat df = new DecimalFormat("#.######");
        List<TransactionHistoryDto> collect = historyRepository.findAll().stream()
                .map(history -> {
                    TransactionHistoryDto dto = modelMapper.map(history, TransactionHistoryDto.class);

                    String formattedQuantity = df.format(dto.getQuantity());
                    formattedQuantity = formattedQuantity.replace(",", ".");
                    String formattedPrice = df.format(dto.getPrice());
                    formattedPrice = formattedPrice.replace(",", ".");
                    dto.setQuantity(BigDecimal.valueOf(Double.parseDouble(formattedQuantity)));
                    dto.setPrice(BigDecimal.valueOf(Double.parseDouble(formattedPrice)));
                    return dto;
                })
                .collect(Collectors.toList());

        return collect;
    }

    public void saveTransactionHistory(TransactionHistory transactionHistory) {
        this.historyRepository.save(transactionHistory);
    }

    @Override
    public String getCurrentBalanceState() {
        List<UserCrypto> userCryptos = userCryptoRepository.findAll();
        BigDecimal  currentBalance = BigDecimal.ZERO;
        Map<String, CryptoCurrency> cryptoCurrencies = krakenWebSocketClient.getCryptoCurrencies();
        for (UserCrypto userCrypto : userCryptos) {
            BigDecimal liveBidPrice = cryptoCurrencies.get(userCrypto.getCryptoCurrency().getPair()).getBidPrice();
            currentBalance = currentBalance.add(liveBidPrice.multiply(userCrypto.getQuantity()));
        }

        return getFormatedBalanceString(currentBalance);
    }


    @Override
    public String getFormatedBalanceString(BigDecimal currentBalance) {
        DecimalFormat df = new DecimalFormat("#.####");
        String balanceState = "";

        User user = userRepository.getReferenceById(1L);

        BigDecimal totalAmount = currentBalance.add(user.getBalance());

        BigDecimal initialCapital = new BigDecimal("10000");

        if (totalAmount.compareTo(initialCapital) > 0) {
            BigDecimal profit = totalAmount.subtract(initialCapital);
            String formattedProfit = df.format(profit);
            balanceState = "Profit: $" + formattedProfit;
        } else if (totalAmount.compareTo(initialCapital) < 0) {
            BigDecimal loss = initialCapital.subtract(totalAmount);
            String formattedLoss = df.format(loss);
            balanceState = "Loss: $" + formattedLoss;
        } else {
            balanceState = "";
        }

        return balanceState;
    }
}
