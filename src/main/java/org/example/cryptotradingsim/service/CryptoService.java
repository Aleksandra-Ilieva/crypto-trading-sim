package org.example.cryptotradingsim.service;

import org.example.cryptotradingsim.model.entity.CryptoCurrency;

import java.util.Map;

public interface CryptoService {

    void connect();

    Map<String, CryptoCurrency> getCryptoCurrencies();

    void initCryptoCurrency();
}
