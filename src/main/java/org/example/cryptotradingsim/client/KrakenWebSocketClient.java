package org.example.cryptotradingsim.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cryptotradingsim.model.entity.CryptoCurrency;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KrakenWebSocketClient extends WebSocketClient {

    private static final String KRAKEN_WS_URL = "wss://ws.kraken.com";
    private static final String[] PAIRS = {
            "BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "ADA/USD",
            "DOGE/USD", "DOT/USD", "AVAX/USD", "LINK/USD", "MATIC/USD",
            "UNI/USD", "ATOM/USD", "LTC/USD", "ALGO/USD", "FIL/USD",
            "XTZ/USD", "EOS/USD", "AAVE/USD", "GRT/USD", "XLM/USD"
    };

    private final Map<String, CryptoCurrency> cryptoCurrencies = new ConcurrentHashMap<>();

    public KrakenWebSocketClient() throws Exception {
        super(new URI(KRAKEN_WS_URL), new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to Kraken WebSocket.");
        subscribeToPairs();
    }

    @Override
    public void onMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);


            if (jsonNode.isArray() && jsonNode.size() > 1) {
                JsonNode data = jsonNode.get(1); // Вземаме данните след идентификатора

                if (data.has("a") && data.has("b")) {

                    BigDecimal askPrice = BigDecimal.valueOf(data.get("a").get(0).asDouble());


                    BigDecimal bidPrice = BigDecimal.valueOf(data.get("b").get(0).asDouble());


                    String pair = jsonNode.get(3).asText();


                    CryptoCurrency cryptoCurrency = cryptoCurrencies.get(pair);
                    if (cryptoCurrency != null) {

                        cryptoCurrency.setAskPrice(askPrice);
                        cryptoCurrency.setBidPrice(bidPrice);
                    } else {

                        cryptoCurrency = new CryptoCurrency(pair, askPrice, bidPrice);
                    }


                    cryptoCurrencies.put(pair, cryptoCurrency);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed connection with Kraken.");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }


    private void subscribeToPairs() {
        for (String pair : PAIRS) {
            String subscribeMessage = "{\"event\": \"subscribe\", \"pair\": [\"" + pair + "\"], \"subscription\": {\"name\": \"ticker\"}}";
            send(subscribeMessage);
        }
    }


    public Map<String, CryptoCurrency> getCryptoCurrencies() {
        return cryptoCurrencies;
    }
}

