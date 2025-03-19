package org.example.cryptotradingsim.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.example.cryptotradingsim.repository.CryptoRepository;
import org.example.cryptotradingsim.service.CryptoService;
import org.example.cryptotradingsim.client.KrakenWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CryptoServiceImpl implements CryptoService {

    private final KrakenWebSocketClient client;

    private final CryptoRepository cryptoRepository;


    @Autowired
    public CryptoServiceImpl(KrakenWebSocketClient client, CryptoRepository cryptoRepository) {
        this.client = client;
        this.cryptoRepository = cryptoRepository;
    }

    @PostConstruct
    public void connect() {
        try {
            client.connect();
            Thread.sleep(3000);
            initCryptoCurrency();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connecting to WebSocket: " + e.getMessage());
        }
    }

    @Override
    public void initCryptoCurrency() {
        Map<String, CryptoCurrency> realTimeCryptoCurrencies = client.getCryptoCurrencies();
        cryptoRepository.saveAll(realTimeCryptoCurrencies.values());
    }

    @Override
    public Map<String, CryptoCurrency> getCryptoCurrencies() {
        return client.getCryptoCurrencies();
    }


}

