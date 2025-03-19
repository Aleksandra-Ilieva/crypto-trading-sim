package org.example.cryptotradingsim.service.impl;

import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.example.cryptotradingsim.repository.CryptoRepository;
import org.example.cryptotradingsim.client.KrakenWebSocketClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoServiceImplTest {

    @Mock
    private KrakenWebSocketClient client;

    @Mock
    private CryptoRepository cryptoRepository;

    @InjectMocks
    private CryptoServiceImpl cryptoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitCryptoCurrency() {
        Map<String, CryptoCurrency> mockCryptoCurrencies = new HashMap<>();
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setPair("BTC/USD");
        mockCryptoCurrencies.put("BTC/USD", cryptoCurrency);
        when(client.getCryptoCurrencies()).thenReturn(mockCryptoCurrencies);
        cryptoService.initCryptoCurrency();
        verify(cryptoRepository, times(1)).saveAll(mockCryptoCurrencies.values());
    }

    @Test
    void testGetCryptoCurrencies() {
        Map<String, CryptoCurrency> mockCryptoCurrencies = new HashMap<>();
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setPair("BTC/USD");
        mockCryptoCurrencies.put("BTC/USD", cryptoCurrency);

        when(client.getCryptoCurrencies()).thenReturn(mockCryptoCurrencies);


        Map<String, CryptoCurrency> result = cryptoService.getCryptoCurrencies();


        assertEquals(mockCryptoCurrencies, result);
        verify(client, times(1)).getCryptoCurrencies();
    }



}
